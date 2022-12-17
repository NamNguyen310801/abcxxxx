package com.example.myappmusic.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Video implements Serializable {

@SerializedName("IdVideo")
@Expose
private String idVideo;
@SerializedName("TenVideo")
@Expose
private String tenVideo;
@SerializedName("TenTacGia")
@Expose
private String tenTacGia;
@SerializedName("HinhVideo")
@Expose
private String hinhVideo;
@SerializedName("LinkVideo")
@Expose
private String linkVideo;

public String getIdVideo() {
return idVideo;
}

public void setIdVideo(String idVideo) {
this.idVideo = idVideo;
}

public String getTenVideo() {
return tenVideo;
}

public void setTenVideo(String tenVideo) {
this.tenVideo = tenVideo;
}

public String getTenTacGia() {
return tenTacGia;
}

public void setTenTacGia(String tenTacGia) {
this.tenTacGia = tenTacGia;
}

public String getHinhVideo() {
return hinhVideo;
}

public void setHinhVideo(String hinhVideo) {
this.hinhVideo = hinhVideo;
}

public String getLinkVideo() {
return linkVideo;
}

public void setLinkVideo(String linkVideo) {
this.linkVideo = linkVideo;
}

}