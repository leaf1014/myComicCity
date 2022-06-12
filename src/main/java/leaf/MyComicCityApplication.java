package leaf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("leaf/mapper")
@SpringBootApplication
public class MyComicCityApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyComicCityApplication.class, args);
    }

}
