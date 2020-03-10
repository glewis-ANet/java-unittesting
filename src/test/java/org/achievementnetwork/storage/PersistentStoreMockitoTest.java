package org.achievementnetwork.storage;

import org.achievementnetwork.domain.Frobnicator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public
class PersistentStoreMockitoTest {

    @Mock
    private KeyValueStore mockStore;

    @Mock
    private Serialiser mockSerialiser;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Captor
    ArgumentCaptor<String> keyCaptor;

    @Captor
    ArgumentCaptor<String> valueCaptor;

    private PersistentStore store;

    @Before
    public
    void setUp() {
        store = new PersistentStore(mockStore, mockSerialiser);
    }

    // Happy path
    // Mockito mocks don't require verification and don't complain when unexpected calls are made
    // Mock verification
    @Test
    public
    void store_ValidFrobnicator_Success()
        throws Exception {

        // Arrange
        Frobnicator frob = new Frobnicator(UUID.randomUUID().toString(), new Object(), "");
        when(mockSerialiser.dehydrate(any(Frobnicator.class))).thenReturn("");

        // Act
        store.store(frob);

        // Assert
        verify(mockStore).store(anyString(), anyString());
    }

    // Simple failure test
    @Test(expected=IllegalArgumentException.class)
    public
    void store_InvalidFrobnicator_IllegalArgumentException()
        throws Exception {

        // Arrange
        Frobnicator frob = new Frobnicator(null, new Object(), "");

        // Act
        store.store(frob);

        // Assert
    }

    // Verify the exception that is thrown
    // Note that this can also be done by wrapping the action in a try/catch
    // and adding asserts into the catch block.
    @Test
    public
    void store_ValidFrobnicatorStoreFailure_IOException()
        throws Exception {

        // Arrange
        String storageErrorMessage = "Storage exception";
        Frobnicator frob = new Frobnicator(UUID.randomUUID().toString(), new Object(), "");
        when(mockSerialiser.dehydrate(frob)).thenReturn("");
        doThrow(new IOException(storageErrorMessage)).when(mockStore).store(anyString(), anyString());

        // Assert
        thrown.expect(IOException.class);
        thrown.expectMessage(storageErrorMessage);

        // Act
        store.store(frob);
    }

    // This test is too tightly coupled
    // Used to demonstrate argument capture
    @Test
    public
    void store_ValidFrobnicatorCapture_Success()
        throws Exception {

        // Arrange
        String key = UUID.randomUUID().toString();
        String serialised = "Corn flakes";
        Frobnicator frob = new Frobnicator(key, new Object(), "");
        when(mockSerialiser.dehydrate(any(Frobnicator.class))).thenReturn(serialised);

        // Act
        store.store(frob);

        // Assert
        verify(mockStore).store(keyCaptor.capture(), valueCaptor.capture());
        assertEquals(key, keyCaptor.getValue());
        assertEquals(serialised, valueCaptor.getValue());
    }

    @Test
    public
    void fetch_NonNullKey_Success()
        throws Exception {

        // Arrange
        String key = UUID.randomUUID().toString();
        Frobnicator frob = new Frobnicator(key, new Object(), "");
        when(mockStore.fetch(anyString())).thenReturn("");
        when(mockSerialiser.hydrate(anyString())).thenReturn(frob);

        // Act
        Frobnicator result = store.fetch(key);

        // Assert
        assertNotNull(result);
    }
}
