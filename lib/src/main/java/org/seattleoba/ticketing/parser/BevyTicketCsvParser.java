package org.seattleoba.ticketing.parser;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.seattleoba.ticketing.model.BevyTicket;

import java.io.IOException;

import java.io.InputStream;
import java.util.List;

/**
 * Parser for Bevy CSV file
 */
public class BevyTicketCsvParser {
    /**
     * Parses the CSV file from Bevy.
     *
     * @param csvInputStream InputStream for Bevy CSV file.
     * @return List of Bevy tickets.
     */
    public List<BevyTicket> parseCsvFile(final InputStream csvInputStream) {
        final CsvMapper csvMapper = new CsvMapper();
        csvMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        final CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
        final List<BevyTicket> tickets;
        final MappingIterator<BevyTicket> mappingIterator;

        try {
            mappingIterator = csvMapper
                    .readerFor(BevyTicket.class)
                    .with(csvSchema)
                    .readValues(csvInputStream);
            tickets = mappingIterator.readAll();
        } catch (final IOException exception) {
            throw new RuntimeException(exception);
        }

        return tickets;
    }
}
