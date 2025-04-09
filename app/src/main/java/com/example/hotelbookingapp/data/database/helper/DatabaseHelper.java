package com.example.hotelbookingapp.data.database.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "HotelBooking.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // User table
        db.execSQL("CREATE TABLE User (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "email TEXT UNIQUE NOT NULL," +
                "password TEXT NOT NULL," +
                "role TEXT NOT NULL" +   // 'user' or 'admin'
                ");");

        // Category table
        db.execSQL("CREATE TABLE Category (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL" +
                ");");

        // Hotel table
        db.execSQL("CREATE TABLE Hotel (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "location TEXT NOT NULL," +
                "description TEXT," +
                "category_id INTEGER," +
                "FOREIGN KEY (category_id) REFERENCES Category(id)" +
                ");");

        // Room table
        db.execSQL("CREATE TABLE Room (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "hotel_id INTEGER," +
                "type TEXT NOT NULL," +
                "price REAL NOT NULL," +
                "is_available INTEGER DEFAULT 1," +
                "FOREIGN KEY (hotel_id) REFERENCES Hotel(id)" +
                ");");

        // Booking table
        db.execSQL("CREATE TABLE Booking (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER," +
                "room_id INTEGER," +
                "check_in DATE NOT NULL," +
                "check_out DATE NOT NULL," +
                "status TEXT DEFAULT 'active'," +   // 'active', 'cancelled'
                "FOREIGN KEY (user_id) REFERENCES User(id)," +
                "FOREIGN KEY (room_id) REFERENCES Room(id)" +
                ");");

        // Review table
        db.execSQL("CREATE TABLE Review (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER," +
                "hotel_id INTEGER," +
                "rating INTEGER NOT NULL," +  // 1 - 5 stars
                "comment TEXT," +
                "FOREIGN KEY (user_id) REFERENCES User(id)," +
                "FOREIGN KEY (hotel_id) REFERENCES Hotel(id)" +
                ");");

        // Payment table (nếu bạn muốn tích hợp thanh toán mô phỏng)
        db.execSQL("CREATE TABLE Payment (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "booking_id INTEGER," +
                "amount REAL NOT NULL," +
                "payment_date DATE," +
                "method TEXT," +  // e.g., 'Momo', 'Bank', etc.
                "status TEXT DEFAULT 'pending'," +  // 'paid', 'pending'
                "FOREIGN KEY (booking_id) REFERENCES Booking(id)" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables
        db.execSQL("DROP TABLE IF EXISTS Payment");
        db.execSQL("DROP TABLE IF EXISTS Review");
        db.execSQL("DROP TABLE IF EXISTS Booking");
        db.execSQL("DROP TABLE IF EXISTS Room");
        db.execSQL("DROP TABLE IF EXISTS Hotel");
        db.execSQL("DROP TABLE IF EXISTS Category");
        db.execSQL("DROP TABLE IF EXISTS User");
        // Recreate
        onCreate(db);
    }
}
