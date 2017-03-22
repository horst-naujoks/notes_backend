package ch.adesso.notes_backend.util;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.springframework.data.convert.Jsr310Converters.DateToLocalDateTimeConverter;
import org.springframework.data.convert.Jsr310Converters.LocalDateTimeToDateConverter;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDateTime, Date> {

	@Override
	public Date convertToDatabaseColumn(LocalDateTime date) {
		return LocalDateTimeToDateConverter.INSTANCE.convert(date);
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Date date) {
		return DateToLocalDateTimeConverter.INSTANCE.convert(date);
	}
}