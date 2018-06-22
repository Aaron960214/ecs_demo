package com.aliyun.ecs.vpc.menu;

/**
 *
 * <p>Copyright: All Rights Reserved</p>
 * <p>Company:</p>
 * <p>Description: 下一跳的类型 </p>
 * <p>Author:qbLiu/刘琪斌</p>
 */
public enum NextHopType {

    Instance("ECS实例"),

    HaVip("高可用虚拟IP"),

    RouterInterface("路由器接口");


    private String description;


    NextHopType() {
    }

    NextHopType(String description) {
        this.description = description;
    }


//    @JsonCreator
    public static NextHopType forValue(String value) {
        if (value != null)
        {
            for (NextHopType v : NextHopType.values()) {
                if (v.name().equalsIgnoreCase(value))
                    return v;
            }
        }
        return null;
    }

    /**
     * <br/>Description:当调用toJSON的时候调用
     * @return
     */
//    @JsonValue
    public NextHopType.NextHopTypeBean value() {
        NextHopType.NextHopTypeBean bean = new NextHopType.NextHopTypeBean(this.name(),description);
        return bean;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    class NextHopTypeBean{
        private String name;
        private String description;
        public String getName(){
            return name;
        }
        public void setName(String name){
            this.name = name;
        }
        public String getDescription(){
            return description;
        }
        public void setDescription(String description){
            this.description = description;
        }
        public NextHopTypeBean(String name,String description){
            super();
            this.name = name;
            this.description = description;
        }
    }


}


