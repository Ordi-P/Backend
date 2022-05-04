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

    public void aliPay(HttpServletRequest request, HttpServletResponse response, String bookId) throws IOException {
        DefaultAlipayClient client = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.APP_ID, AlipayConfig.APP_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.sign_type);
        AlipayTradePagePayRequest alipayTradePagePayRequest = new AlipayTradePagePayRequest();
        //alipayTradePagePayRequest.setNotifyUrl(AlipayConfig.notify_url + bookId);
        alipayTradePagePayRequest.setReturnUrl(AlipayConfig.return_url + bookId);
        Date startDate = borrowDao.queryBorrowDateByBookID(Long.parseLong(bookId));
        long startTime = startDate.getTime() + 10*24*60*60*1000;
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
        List<UserBorrowInfo> userBorrowInfos = borrowDao.queryUserBorrowInfoByID(userId);
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
    public boolean returnBook(String bookId) {
        Long no = Long.parseLong(bookId);
        Date date = borrowDao.queryBorrowDateByBookID(no);
        java.util.Date tempDate = new java.util.Date();
        if(tempDate.getTime() - (date.getTime() + 10*24*60*60*1000) > 0){
            return false;
        }
        String userId = borrowDao.queryBorrowerByBookID(no);
        int res = borrowDao.deleteBorrowRecordByBookId(no);
        bookDao.updateBookAvailability(no,true);
        List<UserBorrowInfo> userBorrowInfos = borrowDao.queryUserBorrowInfoByID(userId);
        Iterator<UserBorrowInfo> iterator = userBorrowInfos.iterator();
        while(iterator.hasNext()){
            UserBorrowInfo borrowInfo = iterator.next();
            Date borrowDate = borrowInfo.getBorrowDate();
            long endTime = borrowDate.getTime() + 10*24*60*60*1000;
            long nowTime = new java.util.Date().getTime();
            long bias = nowTime - endTime;
            if(bias > 0){
                userDao.updateUserEnable(false,userId);
                return true;
            }
        }
        userDao.updateUserEnable(true,userId);
        if(res > 0) return true;
        return false;
    }

    @Override
    public void updateReturnDate(String bookId) {
        long bId = Long.parseLong(bookId);
        Date date = borrowDao.queryBorrowDateByBookID(bId);
        java.util.Date date1 = new java.util.Date();
        long newDate = date1.getTime();
        Date date2 = new Date(newDate);
        borrowDao.updateBorrowRecord(bId,date2);
        String userId = borrowDao.queryBorrowerByBookID(bId);
        List<UserBorrowInfo> userBorrowInfos = borrowDao.queryUserBorrowInfoByID(userId);
        Iterator<UserBorrowInfo> iterator = userBorrowInfos.iterator();
        while(iterator.hasNext()){
            UserBorrowInfo borrowInfo = iterator.next();
            Date borrowDate = borrowInfo.getBorrowDate();
            long endTime = borrowDate.getTime() + 11*24*60*60*1000;
            long nowTime = new java.util.Date().getTime();
            long bias = nowTime - endTime;
            if(bias > 0){
                userDao.updateUserEnable(false,userId);
                return;
            }
        }
        userDao.updateUserEnable(true,userId);
    }
}
