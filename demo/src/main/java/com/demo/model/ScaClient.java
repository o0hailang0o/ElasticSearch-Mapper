package com.demo.model;

import com.demo.elasticsearch.annotations.Document;
import com.demo.elasticsearch.annotations.Id;

import java.util.Date;

/**
 * @author liujian on 2018/11/23.
 */
@Document(index = "f-sca-client",type = "f-sca-client")
public class ScaClient{

    @Id
    private Long id;

    private String uuid;

    private String name;

    private String address;

    private String user;

    private String email;

    private String phone;

    private String phoneMore;

    private String bussinessScope;

    private Long provinceId;

    private String provinceName;

    private Long cityId;

    private String cityName;

    private Long districtId;

    private String districtName;

    private Long townshipId;

    private String townshipName;

    private Long villageId;

    private String villageName;

    private String longitude;

    private String latitude;

    private Integer type;

    private Integer subType;

    private Integer thirdType;

    private Integer accurateLevel;

    private String sourceUuid;

    private Long sourceId;

    private Long posId;

    private Long categoryId;

    private String source;

    private String isDelete;

    private Date createAt;

    private Date updateAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneMore() {
        return phoneMore;
    }

    public void setPhoneMore(String phoneMore) {
        this.phoneMore = phoneMore;
    }

    public String getBussinessScope() {
        return bussinessScope;
    }

    public void setBussinessScope(String bussinessScope) {
        this.bussinessScope = bussinessScope;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Long getTownshipId() {
        return townshipId;
    }

    public void setTownshipId(Long townshipId) {
        this.townshipId = townshipId;
    }

    public String getTownshipName() {
        return townshipName;
    }

    public void setTownshipName(String townshipName) {
        this.townshipName = townshipName;
    }

    public Long getVillageId() {
        return villageId;
    }

    public void setVillageId(Long villageId) {
        this.villageId = villageId;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public Integer getThirdType() {
        return thirdType;
    }

    public void setThirdType(Integer thirdType) {
        this.thirdType = thirdType;
    }

    public Integer getAccurateLevel() {
        return accurateLevel;
    }

    public void setAccurateLevel(Integer accurateLevel) {
        this.accurateLevel = accurateLevel;
    }

    public String getSourceUuid() {
        return sourceUuid;
    }

    public void setSourceUuid(String sourceUuid) {
        this.sourceUuid = sourceUuid;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Long getPosId() {
        return posId;
    }

    public void setPosId(Long posId) {
        this.posId = posId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "ScaClient{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", user='" + user + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", phoneMore='" + phoneMore + '\'' +
                ", bussinessScope='" + bussinessScope + '\'' +
                ", provinceId=" + provinceId +
                ", provinceName='" + provinceName + '\'' +
                ", cityId=" + cityId +
                ", cityName='" + cityName + '\'' +
                ", districtId=" + districtId +
                ", districtName='" + districtName + '\'' +
                ", townshipId=" + townshipId +
                ", townshipName='" + townshipName + '\'' +
                ", villageId=" + villageId +
                ", villageName='" + villageName + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", type=" + type +
                ", subType=" + subType +
                ", thirdType=" + thirdType +
                ", accurateLevel=" + accurateLevel +
                ", sourceUuid='" + sourceUuid + '\'' +
                ", sourceId=" + sourceId +
                ", posId=" + posId +
                ", categoryId=" + categoryId +
                ", source='" + source + '\'' +
                ", isDelete='" + isDelete + '\'' +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                '}';
    }
}
