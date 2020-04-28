package de.dhbw.handycrab.helper;

import android.widget.Toast;
import de.dhbw.handycrab.Program;
import de.dhbw.handycrab.R;
import de.dhbw.handycrab.backend.BackendConnectionException;
import de.dhbw.handycrab.backend.IHandyCrabDataHandler;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.util.concurrent.ExecutionException;

public class DataHelper {

    @Inject
    IHandyCrabDataHandler dataHandler;

    @Inject
    IDataCache dataCache;

    @Inject
    public DataHelper(IDataCache dataCache, IHandyCrabDataHandler dataHandler) {
        this.dataCache = dataCache;
        this.dataHandler = dataHandler;
    }

    public String getUsernameFromId(ObjectId userId) {
        String userName;
        if (dataCache.contains(userId.toString())) {
            userName = dataCache.retrieve(userId.toString()).toString();
        }
        else {
            try {
                userName = dataHandler.getUsernameAsync(userId).get();
                dataCache.store(userId.toString(), userName);
            }
            catch (ExecutionException | InterruptedException e) {
                if (e.getCause() instanceof BackendConnectionException) {
                    BackendConnectionException ex = (BackendConnectionException) e.getCause();
                    Toast.makeText(Program.getAppContext(), ex.getDetailedMessage(Program.getAppContext()), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(Program.getAppContext(), Program.getAppContext().getString(R.string.unknownError), Toast.LENGTH_SHORT).show();
                }
                userName = userId.toHexString();
            }
        }
        return userName;
    }
}