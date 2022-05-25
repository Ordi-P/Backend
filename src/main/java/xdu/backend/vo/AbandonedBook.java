package xdu.backend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 被删除的书籍信息
 *
 * @author 邓乐丰
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbandonedBook {
    String bookName;

    Long bookID;

    String ISBNNumber;

    String reason;
}
