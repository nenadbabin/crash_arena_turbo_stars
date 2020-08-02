package com.example.pmu_projekat.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "chassis"
)
public class Chassis {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "picture")
    public String picture;

    @ColumnInfo(name = "health")
    public int health;

    @ColumnInfo(name = "energy")
    public int energy;
}