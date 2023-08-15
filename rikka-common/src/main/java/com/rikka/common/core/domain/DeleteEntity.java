package com.rikka.common.core.domain;

import java.io.Serializable;

/**
 * Entity基类
 * 
 * @author rikka
 */
public class DeleteEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 备注 */
    private Long[] ids;

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }
}
