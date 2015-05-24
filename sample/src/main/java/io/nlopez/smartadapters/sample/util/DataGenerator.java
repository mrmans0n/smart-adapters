package io.nlopez.smartadapters.sample.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import io.nlopez.smartadapters.sample.model.Place;
import io.nlopez.smartadapters.sample.model.User;

/**
 * Created by mrm on 24/5/15.
 */
public class DataGenerator {
    private static final Random RANDOM = new Random();

    private static final List<String> names = Arrays.asList("Maria", "Pablo", "Nacho", "Concha", "Pepe", "Juan", "John", "Paul", "Kevin", "Anacleto", "Perry", "Tyler", "Stan", "Kyle", "Eric", "Kenny");
    private static final List<String> lastNames = Arrays.asList("Perez", "Lopez", "Fernandez", "Mason", "Lee", "Who", "Doe", "Vergo", "Cifuentes", "Menchu", "Fuertes", "Funke", "MacGyver");
    private static final List<String> roles = Arrays.asList("User", "Admin", "VIP");

    public static List<User> generateUsers(int n) {
        List<User> result = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            String name = names.get(RANDOM.nextInt(names.size()));
            String last = lastNames.get(RANDOM.nextInt(lastNames.size()));
            String role = roles.get(RANDOM.nextInt(roles.size()));
            User user = new User(name, last, role, "http://lorempixel.com/200/200/people?rand=" + RANDOM.nextInt());
            result.add(user);
        }
        return result;
    }

    public static List<Place> generatePlaces(int n) {
        List<Place> result = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            int length = 5 + RANDOM.nextInt(10);
            StringBuilder sb = new StringBuilder(length);
            for (int j = 0; j < length; j++) {
                char tmp = (char) ('a' + RANDOM.nextInt('z' - 'a'));
                sb.append(tmp);
            }
            Place place = new Place(sb.toString(), "http://lorempixel.com/500/200/city?rand=" + RANDOM.nextInt());
            result.add(place);
        }
        return result;
    }

    public static List generateMix(int n) {
        List<User> users = generateUsers(n);
        List<Place> places = generatePlaces(n);
        List result = new ArrayList(n);
        for (int i = 0; i < n; i++) {
            int index = RANDOM.nextInt(n);
            if (RANDOM.nextBoolean()) {
                result.add(users.get(index));
            } else {
                result.add(places.get(index));
            }
        }
        return result;
    }

}
