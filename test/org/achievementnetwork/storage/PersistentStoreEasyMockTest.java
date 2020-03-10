package storage;

import domain.Frobnicator;
import org.easymock.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.UUID;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

@RunWith(EasyMockRunner.class)
public
class PersistentStoreEasyMockTest
    extends EasyMockSupport {

    @Mock
    private KeyValueStore mockStore;

    @Mock
    private Serialiser mockSerialiser;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    Capture<String> keyCaptor = newCapture();

    Capture<String> valueCaptor = newCapture();

    //@TestSubject
    private PersistentStore store;

    @Before
    public
    void setUp() {
        store = new PersistentStore(mockStore, mockSerialiser);
    }

    // Happy path
    // Mock verification
    @Test
    public
    void store_ValidFrobnicator_Success()
        throws Exception {

        // Arrange
        Frobnicator frob = new Frobnicator(UUID.randomUUID().toString(), new Object(), "");
        expect(mockSerialiser.dehydrate(isA(Frobnicator.class))).andReturn("");
        mockStore.store(anyString(), anyString());

        replayAll();

        // Act
        store.store(frob);

        // Assert
        verify(mockStore);
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
    void store_ValidFrobnicatorSerialiserFailure_IOException()
        throws Exception {

        // Arrange
        String storageErrorMessage = "Storage exception";
        Frobnicator frob = new Frobnicator(UUID.randomUUID().toString(), new Object(), "");
        expect(mockSerialiser.dehydrate(frob)).andReturn("");
        mockStore.store(anyString(), anyString());
        expectLastCall().andThrow(new IOException(storageErrorMessage));

        replayAll();

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
        expect(mockSerialiser.dehydrate(isA(Frobnicator.class))).andReturn(serialised);
        mockStore.store(capture(keyCaptor), capture(valueCaptor));

        replayAll();

        // Act
        store.store(frob);

        // Assert
        verify(mockStore);
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
        expect(mockStore.fetch(anyString())).andReturn("");
        expect(mockSerialiser.hydrate(anyString())).andReturn(frob);

        replayAll();

        // Act
        Frobnicator result = store.fetch(key);

        // Assert
        assertNotNull(result);
    }
}