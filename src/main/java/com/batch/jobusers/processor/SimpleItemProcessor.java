package com.batch.jobusers.processor;

import org.springframework.batch.item.ItemProcessor;

/**
 * Processa cada item transformando para UPPERCASE.
 */
public class SimpleItemProcessor implements ItemProcessor<String, String> {
    @Override
    public String process(String item) throws Exception {
        return item.toUpperCase();
    }
}
