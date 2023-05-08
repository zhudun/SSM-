package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Books;
import generator.service.BooksService;
import generator.mapper.BooksMapper;
import org.springframework.stereotype.Service;

/**
* @author 23101
* @description 针对表【books】的数据库操作Service实现
* @createDate 2023-03-22 19:37:11
*/
@Service
public class BooksServiceImpl extends ServiceImpl<BooksMapper, Books>
    implements BooksService{

}




