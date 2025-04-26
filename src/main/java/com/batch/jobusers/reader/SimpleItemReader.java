package com.batch.jobusers.reader;

import java.util.Arrays;

import org.springframework.batch.item.support.ListItemReader;

/**
 * Simula uma leitura de dados em memória.
 */
public class SimpleItemReader extends ListItemReader<String> {
    public SimpleItemReader() {
        super(Arrays.asList("Alice", "Bob", "Charlie", "Diana", "Eve"));
    }
}