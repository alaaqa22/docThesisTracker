package dttTest.business.DAOs;

import dtt.dataAccess.repository.postgres.VoteDAO;
import dtt.dataAccess.utilities.Transaction;
import dtt.global.tansport.Options;
import dtt.global.tansport.Vote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class VotDAOTest {
    @Mock
    Transaction transaction;
    @Mock
    Connection connection;
    @Mock
    PreparedStatement preparedStatement;
    @Mock
    ResultSet resultSet;

    @InjectMocks
    VoteDAO voteDAO; // Initialisiert voteDAO mit Mock-Objekten

    @BeforeEach
    void setUp() throws SQLException {

        MockitoAnnotations.openMocks(this);

        when(transaction.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    void testFindVote() throws Exception {
        Vote vote = new Vote();
        vote.setCirculationId(1);
        vote.setUserId(1);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(anyString())).thenReturn(1);
        when(resultSet.getString(anyString())).thenReturn("reason");

        boolean found = voteDAO.findVote(vote, transaction);

        assertTrue(found);
        verify(preparedStatement, times(1)).setInt(1, vote.getCirculationId());
        verify(preparedStatement, times(1)).setInt(2, vote.getUserId());
    }
    // Test for add()
    @Test
    void testAdd() throws Exception {
        Vote vote = new Vote();
        vote.setUserId(1);
        vote.setCirculationId(2);
        vote.setSelection(Options.STIMME_ZU);
        vote.setDescription("reason");

        when(preparedStatement.executeUpdate()).thenReturn(1);

        voteDAO.add(vote, transaction);

        verify(preparedStatement, times(1)).setInt(1, vote.getUserId());
        verify(preparedStatement, times(1)).setInt(2, vote.getCirculationId());
        verify(preparedStatement, times(1)).setInt(3, vote.getSelection().getValue());
        verify(preparedStatement, times(1)).setString(4, vote.getDescription());
    }

    // Test for remove()
    @Test
    void testRemove() throws Exception {
        Vote vote = new Vote();
        vote.setVoteId(1);

        when(preparedStatement.executeUpdate()).thenReturn(1);

        voteDAO.remove(vote, transaction);

        verify(preparedStatement, times(1)).setInt(1, vote.getVoteId());
    }

    // Test for update()
    @Test
    void testUpdate() throws Exception {
        Vote vote = new Vote();
        vote.setVoteId(1);
        vote.setUserId(1);
        vote.setCirculationId(2);
        vote.setSelection(Options.LEHNE_AB);
        vote.setDescription("reason");

        when(preparedStatement.executeUpdate()).thenReturn(1);

        voteDAO.update(vote, transaction);

        verify(preparedStatement, times(1)).setInt(1, vote.getUserId());
        verify(preparedStatement, times(1)).setInt(2, vote.getCirculationId());
        verify(preparedStatement, times(1)).setInt(3, vote.getSelection().getValue());
        verify(preparedStatement, times(1)).setString(4, vote.getDescription());
        verify(preparedStatement, times(1)).setInt(5, vote.getVoteId());
    }

    // Test for getVotes()
    @Test
    void testGetVotes() throws Exception {
        Vote vote = new Vote();
        vote.setCirculationId(1);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false); // 1 vote returned
        when(resultSet.getInt(anyString())).thenReturn(1);
        when(resultSet.getString(anyString())).thenReturn("reason");

        List<Vote> votes = voteDAO.getVotes(vote, transaction);

        assertEquals(1, votes.size());
        verify(preparedStatement, times(1)).setInt(1, vote.getCirculationId());
    }

}
