package de.dhbw.handycrab.helper;

import dagger.Module;
import dagger.Provides;
import de.dhbw.handycrab.backend.BackendConnector;
import de.dhbw.handycrab.backend.IHandyCrabDataHandler;

import javax.inject.Singleton;

/**
 * Provides implementations for interfaces which are injected
 */
@Module
public class BackendModule {

    /**
     * define implementation for IHandyCrabDataHandler
     *
     * @return data handler which will get injected
     * @see IHandyCrabDataHandler
     */
    @Provides
    @Singleton
    public IHandyCrabDataHandler provideDataHandler() {
        return new BackendConnector();
    }

    /**
     * define implementation for IDataHolder
     *
     * @return data holder which will get injected
     * @see IDataCache
     */
    @Provides
    @Singleton
    public IDataCache provideDataHolder() {
        return new InMemoryCache();
    }
}
