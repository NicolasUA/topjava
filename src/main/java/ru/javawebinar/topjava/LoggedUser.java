package ru.javawebinar.topjava;

/**
 * GKislin
 * 06.03.2015.
 */
public class LoggedUser {
    private static int id = 0;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        LoggedUser.id = id;
    }
}
