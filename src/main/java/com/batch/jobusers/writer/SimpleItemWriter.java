package com.batch.jobusers.writer;

import java.util.List;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

/**
 * Escreve cada item no console.
 */
public class SimpleItemWriter implements ItemWriter<String> {
    @Override
    public void write(Chunk<? extends String> chunk) throws Exception {
        List<? extends String> items = chunk.getItems();
        for (String item : items) {
            System.out.println(">> Escrevendo item: " + item);
        }
    }
}