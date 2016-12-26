package com.charryteam.charryproject.javabean;

/**
 * Created by ChunLin on 2015/12/13.
 */
/*
"cover_path": "\/5\/X\/004aNzlM4H7G5X.jpg",
			"music_id": 3426938,
			"music_name": "I Could Be The One",
			"artist_id": 209038,
			"artist": "Donna Lewis",
			"hascopyright": 1
* */
//图片的完整路径是;http://image02.cdn.chrrs.com/5/X/004aNzlM4H7G5X.jpg

        public  class SongsEntity {
            private String cover_path;//图片的路径
            private int music_id;//歌曲的id
            private String music_name;//歌曲名
            private int artist_id;
            private String artist;//歌曲作者
            private int hascopyright;

            public void setCover_path(String cover_path) {
                this.cover_path =cover_path;
            }

            public void setMusic_id(int music_id) {
                this.music_id = music_id;
            }

            public void setMusic_name(String music_name) {
                this.music_name = music_name;
            }

            public void setArtist_id(int artist_id) {
                this.artist_id = artist_id;
            }

            public void setArtist(String artist) {
                this.artist = artist;
            }

            public void setHascopyright(int hascopyright) {
                this.hascopyright = hascopyright;
            }

            public String getCover_path() {
                return cover_path;
            }

            public int getMusic_id() {
                return music_id;
            }

            public String getMusic_name() {
                return music_name;
            }

            public int getArtist_id() {
                return artist_id;
            }

            public String getArtist() {
                return artist;
            }

            public int getHascopyright() {
                return hascopyright;
            }

    public SongsEntity() {
    }

    public SongsEntity(String cover_path, int music_id, String music_name, int artist_id, String artist, int hascopyright) {
        this.cover_path = cover_path;
        this.music_id = music_id;
        this.music_name = music_name;
        this.artist_id = artist_id;
        this.artist = artist;
        this.hascopyright = hascopyright;
    }

    @Override
    public String toString() {
        return "SongsEntity{" +
                "cover_path='" + cover_path + '\'' +
                ", music_id=" + music_id +
                ", music_name='" + music_name + '\'' +
                ", artist_id=" + artist_id +
                ", artist='" + artist + '\'' +
                ", hascopyright=" + hascopyright +
                '}';
    }
}


