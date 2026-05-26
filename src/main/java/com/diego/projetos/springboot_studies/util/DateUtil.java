package com.diego.projetos.springboot_studies.util;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component // class managed by  Spring: automatically managed objects
// "child" implementations of @Component -> @Controller, @Service, @Repository (MVC arch)
public class DateUtil {
    public String formatLocalDateTimeToDatabaseStyle(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDateTime);
    }
}
