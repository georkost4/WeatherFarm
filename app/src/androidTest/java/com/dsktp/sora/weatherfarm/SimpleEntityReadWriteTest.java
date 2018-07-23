package com.dsktp.sora.weatherfarm;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dsktp.sora.weatherfarm.data.model.Ground.UVindex;
import com.dsktp.sora.weatherfarm.data.repository.AppDatabase;
import com.dsktp.sora.weatherfarm.data.repository.UVindexDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class SimpleEntityReadWriteTest
{
    private UVindexDao mUViDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mUViDao = mDb.uVindexDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        UVindex uVindex = new UVindex(1212,4.5f);

        mUViDao.insertUViEntry(uVindex);
        List<UVindex> UViTableEntries = mUViDao.getAllEntries();
        assertThat(UViTableEntries.get(0).getUvi(), equalTo(uVindex.getUvi()));
    }
}