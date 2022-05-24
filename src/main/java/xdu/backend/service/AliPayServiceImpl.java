package xdu.backend.service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;

import java.io.IOException;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xdu.backend.Dao.BookDao;
import xdu.backend.Dao.BorrowDao;
import xdu.backend.Dao.UserDao;
import xdu.backend.config.AlipayConfig;
import xdu.backend.vo.UserBorrowInfo;

@Service
public class AliPayServiceImpl implements AliPayService {

    @Autowired
    BorrowDao borrowDao;

    @Autowired
    UserDao userDao;

    @Autowired
    BookDao bookDao;

    @Override
    public void aliPay(HttpServletRequest request, HttpServletResponse response, String bookId, String userId) throws IOException {
        DefaultAlipayClient client = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.sign_type);
        AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
        //alipayTradePagePayRequest.setNotifyUrl(AlipayConfig.notify_url + bookId);
        alipayTradePagePayRequest.setReturnUrl(AlipayConfig.return_url + bookId + "&userId=" + userId);
        long startTime = borrowDao.getNoReturnDateByUserIdAndBookId(userId,Long.parseLong(bookId)).getTime();
        long endTime = new java.util.Date().getTime();
        String money = ((Long) ((endTime - startTime)/24/60/60/1000)).toString();
        String subject = "Penalty amount";
        String body = "Penalty amount";
        JSONObject content = new JSONObject();
        String[] tradeNos = UUID.randomUUID().toString().split("-");
        StringBuilder tradeNo = new StringBuilder();
        for (int i = 0; i < tradeNos.length; i++) {
            tradeNo.append(tradeNos[i]);
        }
        content.put("out_trade_no", tradeNo.toString());
        content.put("total_amount", money);
        content.put("subject", subject);
        content.put("body", body);
        content.put("product_code", "FAST_INSTANT_TRADE_PAY");
        alipayTradePagePayRequest.setBizContent(content.toString());
        String form = "";

        try {
            form = ((AlipayTradePagePayResponse) client.pageExecute(alipayTradePagePayRequest)).getBody();
        } catch (AlipayApiException var11) {
            var11.printStackTrace();
        }

        response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
        response.getWriter().write(form);
        response.getWriter().flush();
        response.getWriter().close();
    }

    @Override
    public long returnFine(String userId) {
        List<UserBorrowInfo> userBorrowInfos = borrowDao.queryUserCurrentBorrowInfoByUserID(userId);
        Iterator<UserBorrowInfo> iterator = userBorrowInfos.iterator();
        int res = 0;
        while(iterator.hasNext()){
            UserBorrowInfo next = iterator.next();
            Date date = next.getBorrowDate();
            long endTime = date.getTime() + 10*24*60*60*1000;
            long nowTime = new java.util.Date().getTime();
            long bias = nowTime - endTime;
            if(bias > 0){
                res += bias/24/60/60/1000;
            }
        }
        return res;
    }

    @Override
    public boolean returnBook(String bookId, String userId, HttpServletResponse response) {
        Long no = Long.parseLong(bookId);
        Date date = borrowDao.getNoReturnDateByUserIdAndBookId(userId,no);
        java.util.Date tempDate = new java.util.Date();
        if(tempDate.getTime() - date.getTime() > 0){
            return false;
        }
        borrowDao.updateReturnDateByBookIdAndUserId(userId,no,new Date(new java.util.Date().getTime()));
        borrowDao.updateReturnedByBookIdAndUserId(no,userId,true);
        bookDao.updateBookAvailability(no,true);
        List<UserBorrowInfo> userBorrowInfos = borrowDao.queryUserCurrentBorrowInfoByUserID(userId);
        Iterator<UserBorrowInfo> iterator = userBorrowInfos.iterator();
        while(iterator.hasNext()){
            UserBorrowInfo borrowInfo = iterator.next();
            if (!borrowInfo.getReturned()){
                Date shouldReturnDate = borrowDao.getNoReturnDateByUserIdAndBookId(userId, borrowInfo.getBookID());
                if(shouldReturnDate.getTime() < new java.util.Date().getTime()){
                    userDao.updateUserEnable(false,userId);
                    return true;
                }
            }
        }
        userDao.updateUserEnable(true,userId);
        return true;
    }

    @Override
    public void updateReturnDate(String bookId, String userId) {
        long bId = Long.parseLong(bookId);
        java.util.Date date1 = new java.util.Date();
        long newDate = date1.getTime() + 10*24*60*60*1000;
        Date date2 = new Date(newDate);
        borrowDao.updateReturnDateByBookIdAndUserId(userId,bId,date2);
        List<UserBorrowInfo> userBorrowInfos = borrowDao.queryUserCurrentBorrowInfoByUserID(userId);
        Iterator<UserBorrowInfo> iterator = userBorrowInfos.iterator();
        while(iterator.hasNext()){
            UserBorrowInfo borrowInfo = iterator.next();
            Date borrowDate = borrowInfo.getBorrowDate();
            long endTime = borrowDao.getNoReturnDateByUserIdAndBookId(userId,bId).getTime();
            long nowTime = new java.util.Date().getTime();
            long bias = nowTime - endTime;
            if(bias > 0 && !borrowInfo.getReturned()){
                userDao.updateUserEnable(false,userId);
                return;
            }
        }
        userDao.updateUserEnable(true,userId);
    }

    @Override
    public void payAll(HttpServletRequest request, HttpServletResponse response, String userId) throws IOException {
        DefaultAlipayClient client = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.sign_type);
        AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
        //alipayTradePagePayRequest.setNotifyUrl(AlipayConfig.notify_url + bookId);
        alipayTradePagePayRequest.setReturnUrl("http://localhost:8080/updateAllBorrowDateByUserId?userId="+userId);
        List<UserBorrowInfo> userBorrowInfos = borrowDao.queryUserCurrentBorrowInfoByUserID(userId);
        Iterator<UserBorrowInfo> iterator = userBorrowInfos.iterator();
        long sumMoney = 0l;
        while(iterator.hasNext()){
            UserBorrowInfo borrowInfo = iterator.next();
            Date borrowDate = borrowInfo.getBorrowDate();
            long endTime = borrowDao.getNoReturnDateByUserIdAndBookId(userId,borrowInfo.getBookID()).getTime();
            long nowTime = new java.util.Date().getTime();
            long bias = nowTime - endTime;
            if(bias > 0){
                sumMoney += (Long) (bias/24/60/60/1000);
            }
        }
        String money = ((Long) (sumMoney)).toString();
        String subject = "Penalty amount";
        String body = "Penalty amount";
        JSONObject content = new JSONObject();
        String[] tradeNos = UUID.randomUUID().toString().split("-");
        StringBuilder tradeNo = new StringBuilder();
        for (int i = 0; i < tradeNos.length; i++) {
            tradeNo.append(tradeNos[i]);
        }
        content.put("out_trade_no", tradeNo.toString());
        content.put("total_amount", money);
        content.put("subject", subject);
        content.put("body", body);
        content.put("product_code", "FAST_INSTANT_TRADE_PAY");
        alipayTradePagePayRequest.setBizContent(content.toString());
        String form = "";

        try {
            form = ((AlipayTradePagePayResponse) client.pageExecute(alipayTradePagePayRequest)).getBody();
        } catch (AlipayApiException var11) {
            var11.printStackTrace();
        }

        response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
        response.getWriter().write(form);
        response.getWriter().flush();
        response.getWriter().close();
    }

    @Override
    public void updateAllReturnDateByUserId(String userId) {
        List<UserBorrowInfo> userBorrowInfos = borrowDao.queryUserCurrentBorrowInfoByUserID(userId);
        Iterator<UserBorrowInfo> iterator = userBorrowInfos.iterator();
        while(iterator.hasNext()){
            UserBorrowInfo borrowInfo = iterator.next();
            java.util.Date newBorrowDate = new java.util.Date();
            long shouldReturnTime =  borrowDao.getNoReturnDateByUserIdAndBookId(userId,borrowInfo.getBookID()).getTime();
            long bias = newBorrowDate.getTime() - shouldReturnTime;
            if(bias > 0){
                borrowDao.updateReturnDateByBookIdAndUserId(userId,borrowInfo.getBookID(),new Date(new java.util.Date().getTime() + 10*24*60*60*1000));
                String money = ((Long) ((bias)/24/60/60/1000)).toString();
                borrowDao.updateBorrowRecordFine(borrowInfo.getBookID(), money);
            }
        }
        userDao.updateUserEnable(true,userId);
    }
}
