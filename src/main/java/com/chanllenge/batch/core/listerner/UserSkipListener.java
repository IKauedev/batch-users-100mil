package com.chanllenge.batch.core.listerner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.batch.core.listener.SkipListenerSupport;

import com.chanllenge.batch.entity.user.User;

import lombok.extern.slf4j.Slf4j;

/**
 * Listener que registra falhas de processamento em um arquivo CSV para posterior análise.
 */
@Slf4j
public class UserSkipListener extends SkipListenerSupport<User, User> {
    private static final String ERROR_FILE_PATH = "failed_users.csv";

    @Override
    public void onSkipInProcess(User user, Throwable t) {
        log.error("Usuário ignorado durante processamento: {}", user, t);
        writeError(user, t);
    }

    private void writeError(User user, Throwable t) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.get(ERROR_FILE_PATH).toFile(), true))) {
            writer.write(String.format("%s,%s,%s,%s\n",
                    user.getId(),
                    user.getName(),
                    user.getTeam(),
                    user.getActive(),
                    user.getCountry(),
                    user.getLogs(),
                    user.getScore(),
                    user.getTeam(),
                    t.getMessage().replace(",", " ")));
        } catch (IOException e) {
            log.error("Erro ao escrever no arquivo de falhas: {}", e.getMessage(), e);
        }
    }
}
