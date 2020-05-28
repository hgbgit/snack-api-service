package br.com.snack.apiservice.data.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

@Mapper(componentModel = "spring")
public class DateMapper {

    public ZonedDateTime fromDate(LocalDateTime dateTime, @Context TimeZone timeZone) {
        return dateTime == null ? null : ZonedDateTime.of(dateTime, ZoneId.systemDefault())
                                                      .withZoneSameInstant(timeZone.toZoneId());
    }

}
