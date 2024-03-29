package de.dhbw.handycrab.test;

import de.dhbw.handycrab.backend.BackendConnectionException;
import de.dhbw.handycrab.backend.IHandyCrabDataHandler;
import de.dhbw.handycrab.model.Barrier;
import de.dhbw.handycrab.model.Solution;
import de.dhbw.handycrab.model.User;
import de.dhbw.handycrab.model.Vote;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MockConnector implements IHandyCrabDataHandler {

    private static ObjectId sharedId = ObjectId.get();

    @Override
    public CompletableFuture<User> registerAsync(String email, String username, String password, boolean createToken) throws BackendConnectionException {
        User user = new User(sharedId, username, email);
        return CompletableFuture.completedFuture(user);
    }

    @Override
    public CompletableFuture<User> loginAsync(String emailOrUsername, String password, boolean createToken) {
        User user = new User(sharedId, emailOrUsername, emailOrUsername);
        return CompletableFuture.completedFuture(user);
    }

    @Override
    public CompletableFuture<User> currenUserAsync() {
        User user = new User(sharedId, "Hans", "Hans@mail.com");
        return CompletableFuture.completedFuture(user);
    }

    @Override
    public CompletableFuture<Void> logoutAsync() {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<String> getUsernameAsync(ObjectId id) {
        //return CompletableFuture.supplyAsync(() -> { throw new BackendConnectionException(ErrorCode.USER_NOT_FOUND, 404); });
        return CompletableFuture.completedFuture("mocked Username");
    }

    @Override
    public CompletableFuture<List<Barrier>> getBarriersAsync(double longitude, double latitude, int radius) {
        try {
            Thread.sleep(500);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        Solution s1 = new Solution(ObjectId.get(), "einfach außen rum gehen! einfach außen rum gehen! einfach außen rum gehen! einfach außen rum gehen! einfach außen rum gehen! einfach außen rum gehen!", ObjectId.get(), 12, 42, Vote.NONE);
        Solution s2 = new Solution(ObjectId.get(), "zweifach außen rum gehen!", ObjectId.get(), 421, 546, Vote.NONE);
        Solution s3 = new Solution(ObjectId.get(), "dreifach außen rum gehen!", ObjectId.get(), 567, 85, Vote.NONE);
        Solution s4 = new Solution(ObjectId.get(), "viewfach außen rum gehen!", ObjectId.get(), 456, 4, Vote.NONE);
        Solution s5 = new Solution(ObjectId.get(), "fünffach außen rum gehen!", ObjectId.get(), 6, 784, Vote.NONE);
        List<Solution> solutions = new ArrayList<>();
        solutions.add(s1);
        solutions.add(s2);
        solutions.add(s3);
        solutions.add(s4);
        solutions.add(s5);
        Barrier b1 = new Barrier(ObjectId.get(), sharedId, "Treppe", 8.5, 48.42, "https://www.seo-suedwest.de/images/canonical-herausragend.jpg", "Das ist eine Beschreibung", null, solutions, 43, 23, Vote.NONE);
        Barrier b2 = new Barrier(ObjectId.get(), ObjectId.get(), "Treppe222", 8.499, 48.4202, "https://hbsecurite-dz.com/wp-content/uploads/2019/01/barri%C3%A8re-levante-automatique-fbx.png", "Das ist eine andere Beschreibung", null, solutions, 42, 56, Vote.NONE);

        List<Barrier> list = new ArrayList<>();
        list.add(b1);
        list.add(b2);
        return CompletableFuture.completedFuture(list);
    }

    @Override
    public CompletableFuture<List<Barrier>> getBarriersAsync(String postcode) {
        try {
            Thread.sleep(500);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        Solution s1 = new Solution(ObjectId.get(), "einfach außen rum gehen! einfach außen rum gehen! einfach außen rum gehen! einfach außen rum gehen! einfach außen rum gehen! einfach außen rum gehen!", ObjectId.get(), 12, 42, Vote.NONE);
        Solution s2 = new Solution(ObjectId.get(), "zweifach außen rum gehen!", ObjectId.get(), 421, 546, Vote.NONE);
        Solution s3 = new Solution(ObjectId.get(), "dreifach außen rum gehen!", ObjectId.get(), 567, 85, Vote.NONE);
        Solution s4 = new Solution(ObjectId.get(), "viewfach außen rum gehen!", ObjectId.get(), 456, 4, Vote.NONE);
        Solution s5 = new Solution(ObjectId.get(), "fünffach außen rum gehen!", ObjectId.get(), 6, 784, Vote.NONE);
        List<Solution> solutions = new ArrayList<>();
        solutions.add(s1);
        solutions.add(s2);
        solutions.add(s3);
        solutions.add(s4);
        solutions.add(s5);
        Barrier b1 = new Barrier(ObjectId.get(), sharedId, "Treppe", 42.0, 69.0, "https://www.seo-suedwest.de/images/canonical-herausragend.jpg", "Das ist eine Beschreibung", "72160", solutions, 43, 23, Vote.NONE);
        Barrier b2 = new Barrier(ObjectId.get(), ObjectId.get(), "Treppe222", 41.0, 68.0, "https://hbsecurite-dz.com/wp-content/uploads/2019/01/barri%C3%A8re-levante-automatique-fbx.png", "Das ist eine andere Beschreibung", "72160", solutions, 42, 56, Vote.NONE);

        List<Barrier> list = new ArrayList<>();
        list.add(b1);
        list.add(b2);
        return CompletableFuture.completedFuture(list);
    }

    @Override
    public CompletableFuture<List<Barrier>> getBarriersAsync() {
        try {
            Thread.sleep(500);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        Solution s1 = new Solution(ObjectId.get(), "einfach außen rum gehen! einfach außen rum gehen! einfach außen rum gehen! einfach außen rum gehen! einfach außen rum gehen! einfach außen rum gehen!", ObjectId.get(), 12, 42, Vote.NONE);
        Solution s2 = new Solution(ObjectId.get(), "zweifach außen rum gehen!", ObjectId.get(), 421, 546, Vote.NONE);
        Solution s3 = new Solution(ObjectId.get(), "dreifach außen rum gehen!", ObjectId.get(), 567, 85, Vote.NONE);
        Solution s4 = new Solution(ObjectId.get(), "viewfach außen rum gehen!", ObjectId.get(), 456, 4, Vote.NONE);
        Solution s5 = new Solution(ObjectId.get(), "fünffach außen rum gehen!", ObjectId.get(), 6, 784, Vote.NONE);
        List<Solution> solutions = new ArrayList<>();
        solutions.add(s1);
        solutions.add(s2);
        solutions.add(s3);
        solutions.add(s4);
        solutions.add(s5);
        Barrier b1 = new Barrier(ObjectId.get(), sharedId, "Meine 1", 42.0, 69.0, "https://www.seo-suedwest.de/images/canonical-herausragend.jpg", "Das ist eine Beschreibung", "72160", solutions, 43, 23, Vote.NONE);
        Barrier b2 = new Barrier(ObjectId.get(), ObjectId.get(), "Meine 2", 41.0, 68.0, "https://hbsecurite-dz.com/wp-content/uploads/2019/01/barri%C3%A8re-levante-automatique-fbx.png", "Das ist eine andere Beschreibung", "72160", solutions, 42, 56, Vote.NONE);

        List<Barrier> list = new ArrayList<>();
        list.add(b1);
        list.add(b2);
        return CompletableFuture.completedFuture(list);
    }

    @Override
    public CompletableFuture<Barrier> getBarrierAsync(ObjectId id) {
        return null;
    }

    @Override
    public CompletableFuture<Barrier> addBarrierAsync(String title, double longitude, double latitude, String picture_base64, String description, String postcode, String solution) {
        Barrier b1 = new Barrier(ObjectId.get(), ObjectId.get(), title, longitude, latitude, null, "Diese Barriere wurde hinzugefügt", null, new ArrayList<>(), 43, 23, Vote.NONE);
        return CompletableFuture.completedFuture(b1);
    }

    @Override
    public CompletableFuture<Barrier> modifyBarrierAsync(ObjectId id, String title, String picture_base64, String description) {
        Barrier b1 = new Barrier(id, sharedId, title, 42.0, 69.0, null, description, null, new ArrayList<>(), 43, 23, Vote.NONE);
        return CompletableFuture.completedFuture(b1);
    }

    @Override
    public CompletableFuture<Void> deleteBarrierAsync(ObjectId id) {
        return null;
    }

    @Override
    public CompletableFuture<Barrier> addSolutionAsync(ObjectId barrierID, String solution) {
        Solution s1 = new Solution(ObjectId.get(), "neue super Lösung!", ObjectId.get(), 12, 42, Vote.NONE);
        List<Solution> list = new ArrayList<>();
        list.add(s1);
        Barrier b1 = new Barrier(ObjectId.get(), ObjectId.get(), "add Barrier", 42.0, 69.0, null, "Diese Barriere wurde hinzugefügt", null, list, 43, 23, Vote.NONE);
        return CompletableFuture.completedFuture(b1);
    }

    @Override
    public CompletableFuture<Void> voteBarrierAsync(ObjectId id, Vote vote) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> voteSolutionAsync(ObjectId id, Vote vote) {
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void loadToken(String token, String domain) {

    }

    @Override
    public void saveToken(BiConsumer<String, String> function) {

    }
}
