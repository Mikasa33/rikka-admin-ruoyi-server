package com.rikka.web.controller.system;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.rikka.common.core.domain.DeleteEntity;
import com.rikka.common.core.domain.entity.SysDictType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rikka.common.annotation.Log;
import com.rikka.common.core.controller.BaseController;
import com.rikka.common.core.domain.AjaxResult;
import com.rikka.common.core.domain.entity.SysDictData;
import com.rikka.common.core.page.TableDataInfo;
import com.rikka.common.enums.BusinessType;
import com.rikka.common.utils.StringUtils;
import com.rikka.common.utils.poi.ExcelUtil;
import com.rikka.system.service.ISysDictDataService;
import com.rikka.system.service.ISysDictTypeService;

/**
 * 数据字典信息
 * 
 * @author rikka
 */
@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController
{
    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private ISysDictTypeService dictTypeService;

    @PreAuthorize("@ss.hasPermi('system:dict:list')")
    @GetMapping("/page")
    public AjaxResult page(SysDictData dictData)
    {
        startPage();
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('system:dict:list')")
    @GetMapping("/list")
    public AjaxResult list(SysDictData dictData)
    {
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        return success(list);
    }

    @PreAuthorize("@ss.hasPermi('system:dict:info')")
    @GetMapping("/info")
    public AjaxResult info(Long dictCode)
    {
        return success(dictDataService.selectDictDataById(dictCode));
    }

    @PreAuthorize("@ss.hasPermi('system:dict:add')")
    @Log(title = "字典数据", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody SysDictData dict)
    {
        dict.setCreateBy(getUsername());
        return toAjax(dictDataService.insertDictData(dict));
    }

    @PreAuthorize("@ss.hasPermi('system:dict:edit')")
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult update(@Validated @RequestBody SysDictData dict)
    {
        dict.setUpdateBy(getUsername());
        return toAjax(dictDataService.updateDictData(dict));
    }

    @PreAuthorize("@ss.hasPermi('system:dict:remove')")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete")
    public AjaxResult delete(@Validated @RequestBody DeleteEntity entity)
    {
        dictDataService.deleteDictDataByIds(entity.getIds());
        return success();
    }

    @Log(title = "字典数据", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:dict:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDictData dictData)
    {
        List<SysDictData> list = dictDataService.selectDictDataList(dictData);
        ExcelUtil<SysDictData> util = new ExcelUtil<SysDictData>(SysDictData.class);
        util.exportExcel(response, list, "字典数据");
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    public AjaxResult dictType(@PathVariable String dictType)
    {
        List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
        if (StringUtils.isNull(data))
        {
            data = new ArrayList<SysDictData>();
        }
        return success(data);
    }
}
