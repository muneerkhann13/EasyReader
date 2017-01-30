package com.example.asce.easyreader;

import android.util.Log;

/**
 * Created by Asce on 16/07/16.
 */
public class Word {
    public String title, part_of_speech, meaning, example;
    public String shortlist_tag="unlike";
    public int id;

    public Word() {
    }

    public Word(String title, String part_of_speech, String meaning,String example) {
        this.title = title;
        this.part_of_speech=part_of_speech;
        this.meaning=meaning;
        this.example=example;
    }
    public boolean equals(Object obj) {

        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        Word other = (Word) obj;
        if (!title.equals(other.title))
        {
            return false;
        }
        return true;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getPart_of_speech() {
        return part_of_speech;
    }

    public void setPart_of_speech(String part_of_speech) {
        this.part_of_speech=part_of_speech;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning=meaning;
    }

    public String getExample()
    {
        return example;
    }

    public void setExample(String example)
    {
        this.example=example;
    }

    public void setTag(String tag)
    {
        shortlist_tag=tag;
    }
    public String getTag()
    {
        return shortlist_tag;
    }

    public void setId(int position)
    {
        id=position;
    }
    public int getId()
    {
        return id;
    }

}