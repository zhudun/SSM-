package generator.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName books
 */
@TableName(value ="books")
@Data
public class Books implements Serializable {
    /**
     * 书id
     */
    private Integer bookId;

    /**
     * 书名
     */
    private String bookName;

    /**
     * 数量
     */
    private Integer bookCounts;

    /**
     * 描述
     */
    private String detail;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}