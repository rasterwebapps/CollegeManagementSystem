package com.cms.config;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import graphql.scalars.ExtendedScalars;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

@Configuration
public class GraphQLConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
            .scalar(dateTimeScalarType())
            .scalar(dateScalarType())
            .scalar(timeScalarType())
            .scalar(ExtendedScalars.GraphQLBigDecimal);
    }

    private GraphQLScalarType dateTimeScalarType() {
        return GraphQLScalarType.newScalar()
            .name("DateTime")
            .description("An ISO-8601 encoded UTC date-time string")
            .coercing(new Coercing<Instant, String>() {
                @Override
                public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
                    if (dataFetcherResult instanceof Instant instant) {
                        return instant.toString();
                    }
                    throw new CoercingSerializeException("Expected an Instant object");
                }

                @Override
                public Instant parseValue(Object input) throws CoercingParseValueException {
                    if (input instanceof String s) {
                        return Instant.parse(s);
                    }
                    throw new CoercingParseValueException("Expected a String");
                }

                @Override
                public Instant parseLiteral(Object input) throws CoercingParseLiteralException {
                    if (input instanceof graphql.language.StringValue sv) {
                        return Instant.parse(sv.getValue());
                    }
                    throw new CoercingParseLiteralException("Expected a StringValue");
                }
            })
            .build();
    }

    private GraphQLScalarType dateScalarType() {
        return GraphQLScalarType.newScalar()
            .name("Date")
            .description("An ISO-8601 encoded date string (YYYY-MM-DD)")
            .coercing(new Coercing<LocalDate, String>() {
                @Override
                public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
                    if (dataFetcherResult instanceof LocalDate ld) {
                        return ld.toString();
                    }
                    throw new CoercingSerializeException("Expected a LocalDate object");
                }

                @Override
                public LocalDate parseValue(Object input) throws CoercingParseValueException {
                    if (input instanceof String s) {
                        return LocalDate.parse(s);
                    }
                    throw new CoercingParseValueException("Expected a String");
                }

                @Override
                public LocalDate parseLiteral(Object input) throws CoercingParseLiteralException {
                    if (input instanceof graphql.language.StringValue sv) {
                        return LocalDate.parse(sv.getValue());
                    }
                    throw new CoercingParseLiteralException("Expected a StringValue");
                }
            })
            .build();
    }

    private GraphQLScalarType timeScalarType() {
        return GraphQLScalarType.newScalar()
            .name("Time")
            .description("An ISO-8601 encoded time string (HH:mm:ss)")
            .coercing(new Coercing<LocalTime, String>() {
                @Override
                public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
                    if (dataFetcherResult instanceof LocalTime lt) {
                        return lt.format(DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ROOT));
                    }
                    throw new CoercingSerializeException("Expected a LocalTime object");
                }

                @Override
                public LocalTime parseValue(Object input) throws CoercingParseValueException {
                    if (input instanceof String s) {
                        return LocalTime.parse(s);
                    }
                    throw new CoercingParseValueException("Expected a String");
                }

                @Override
                public LocalTime parseLiteral(Object input) throws CoercingParseLiteralException {
                    if (input instanceof graphql.language.StringValue sv) {
                        return LocalTime.parse(sv.getValue());
                    }
                    throw new CoercingParseLiteralException("Expected a StringValue");
                }
            })
            .build();
    }

}
