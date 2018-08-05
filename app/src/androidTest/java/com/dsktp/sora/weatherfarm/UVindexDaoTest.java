package com.dsktp.sora.weatherfarm;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

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
public class UVindexDaoTest
{
    private UVindexDao mUViDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mUViDao = mDb.uVindexDao();
    }



    @Test
    public void writeUserAndReadInList() {
        //create a dummy UVindex object
        UVindex uVindex = new UVindex(1212,4.5f);
        //insert the dummy object to the database
        mUViDao.insertUViEntry(uVindex);
        //get the list of all  the entries
        List<UVindex> UViTableEntries = mUViDao.getAllEntries();
        assertThat(UViTableEntries.size(),equalTo(1));
        assertThat(UViTableEntries.get(0).getUvi(), equalTo(uVindex.getUvi()));
        assertThat(UViTableEntries.get(0).getDt(), equalTo(uVindex.getDt()));
    }

    @Test
    public void removeEntryFromDatabase()
    {
        //insert one element in the database
        UVindex uVindex = new UVindex(1212,4.5f);
        mUViDao.insertUViEntry(uVindex);

        //get the list of the entries of the database
        List<UVindex> mUViDaoAllEntries = mUViDao.getAllEntries();
        //get the first entry
        UVindex firstEntry = mUViDaoAllEntries.get(0);
        //get the size of the entry list
        int entrySizePreDeletion = mUViDaoAllEntries.size();
        // perform delete method on database
        mUViDao.delete(firstEntry.getDt());
        int ExpectedSizeAfterDeletion = entrySizePreDeletion - 1;
        int ActualSizeAfterDeletion = mUViDao.getAllEntries().size();
        //assert that the size of the table has been minus 1
        assertThat("The size differs after calling remove on dao object",ActualSizeAfterDeletion,equalTo(ExpectedSizeAfterDeletion));
    }


    @After
    public void closeDb() {
        mDb.close();
    }
}