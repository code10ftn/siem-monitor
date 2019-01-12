package com.code10.security.repository.impl;

import com.code10.security.controller.exception.BadRequestException;
import com.code10.security.repository.RuleFileRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class RuleFileRepositoryImpl implements RuleFileRepository {

    private static final String RULES_PATH = Paths.get("..", "SiemRules", "src", "main", "resources", "rules").toString();

    private static final String DROOLS_EXTENSION = ".drl";

    @Override
    public void save(Long id, String rule) {
        final Path rulePath = formatRulePath(id);
        try (final Writer writer = Files.newBufferedWriter(rulePath)) {
            writer.write(rule);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Error saving alarm rule!");
        }
    }

    @Override
    public void deleteById(long id) {
        final Path rulePath = formatRulePath(id);
        final File ruleFile = new File(rulePath.toString());
        final boolean deleted = ruleFile.delete();
        if (!deleted) {
            throw new BadRequestException("Error deleting alarm rule!");
        }
    }

    public void deleteAll() {
        final File folder = new File(RULES_PATH);

        for (File file : folder.listFiles()) {
            if (file.getName().endsWith(DROOLS_EXTENSION)) {
                final boolean deleted = file.delete();
                if (!deleted) {
                    throw new BadRequestException("Error deleting alarm rule!");
                }
            }
        }
    }

    private Path formatRulePath(long id) {
        return Paths.get(RULES_PATH, String.valueOf(id) + DROOLS_EXTENSION);
    }
}
