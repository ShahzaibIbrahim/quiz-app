package com.tt.quizbuilder.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import org.apache.commons.lang3.RandomStringUtils;

public class QuizIdGenerator implements IdentifierGenerator {

    public static final String generatorName = "quizIdGenerator";

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object object) throws HibernateException {
        return RandomStringUtils.randomAlphanumeric(6).toUpperCase();
    }
}

