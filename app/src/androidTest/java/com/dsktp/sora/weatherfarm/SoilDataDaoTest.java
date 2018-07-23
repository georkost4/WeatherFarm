package com.dsktp.sora.weatherfarm;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dsktp.sora.weatherfarm.data.model.Ground.Soil;
import com.dsktp.sora.weatherfarm.data.repository.AppDatabase;
import com.dsktp.sora.weatherfarm.data.repository.SoilDataDao;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */

@RunWith(AndroidJUnit4.class)
public class SoilDataDaoTest
{
    private AppDatabase mDb;
    private SoilDataDao mSoilDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mSoilDao = mDb.soilDataDao();
    }

    @Test
    public void WriteReadToTheTable()
    {
        Soil dummySoilObject = new Soil(1242,289.03,8.40,300);
        mSoilDao.insertSoilDataEntry(dummySoilObject);
        List<Soil> mSoilTableEntries =  mSoilDao.getAllEntries();

        assertThat(mSoilTableEntries.get(0).getDt(),equalTo(dummySoilObject.getDt()));
        assertThat(mSoilTableEntries.get(0).getMoisture(),equalTo(dummySoilObject.getMoisture()));
        assertThat(mSoilTableEntries.get(0).getT10(),equalTo(dummySoilObject.getT10()));
        assertThat(mSoilTableEntries.get(0).getT0(),equalTo(dummySoilObject.getT0()));
    }

    @Test
    public void deleteFromTheTable()
    {
        Soil dummySoilObject = new Soil(1242,289.03,8.40,300);
        mSoilDao.insertSoilDataEntry(dummySoilObject);
        List<Soil> mSoilTableEntries =  mSoilDao.getAllEntries();

        int tableSizeBeforeDelete = mSoilTableEntries.size();
        int ExpectedTableSizeAfterDelete = tableSizeBeforeDelete - 1;

        mSoilDao.delete(dummySoilObject.getDt());

        assertThat(mSoilDao.getAllEntries().size(),equalTo(ExpectedTableSizeAfterDelete));
    }

    @After
    public void CloseDB()
    {
        mDb.close();
    }
}
