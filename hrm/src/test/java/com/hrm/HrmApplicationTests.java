package com.hrm;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.StopWatch;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hrm.dto.Response;
import com.hrm.dto.ResponseDTO;
import com.hrm.entity.*;
import com.hrm.enums.BusinessStatusEnum;
import com.hrm.mapper.DeptMapper;
import com.hrm.mapper.InsuranceMapper;
import com.hrm.mapper.RoleMenuMapper;
import com.hrm.mapper.StaffMapper;
import com.hrm.service.*;
import com.hrm.util.DatetimeUtil;
import com.hrm.util.HutoolExcelUtil;
import com.hrm.util.JWTUtil;
import com.hrm.util.MD5Util;
import com.hrm.vo.DeptWorkTimeVO;
import com.hrm.vo.StaffDeptVO;
import com.hrm.vo.StaffInsuranceVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HrmApplicationTests {

    @Autowired
    private StaffService staffService;

    @Resource
    private StaffMapper staffMapper;

    @Resource
    private RoleMenuService roleMenuService;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private LoginService loginService;

    @Resource
    private MenuService menuService;

    @Resource
    private InsuranceMapper insuranceMapper;

    @Resource
    private DeptMapper deptMapper;

    @Resource
    private WorkTimeService workTimeService;

    @Resource
    private AttendanceService attendanceService;

    @Resource
    private SalaryService salaryService;

    @Resource
    private HomeService homeService;

    @Test
    void test1() {
        ResponseDTO resultDTO = new ResponseDTO();
        System.out.println(resultDTO);
    }

    @Test
    void test2() {
        ResponseDTO resultDTO = new ResponseDTO(200, "success", "ok");
        System.out.println(resultDTO);
    }

    /**
     * ??????????????????
     */
    @Test
    void test3() {
        String path = System.getProperty("user.dir");
        System.out.println(path + "/src/main/java");
    }

    @Test
    void test4() {
        List<Character> list = new ArrayList<>();
        list.add('a');
        list.add('b');
        list.add('c');
        System.out.println(list.indexOf('a'));
        System.out.println(list.indexOf('b'));
        System.out.println(list.indexOf('c'));
        System.out.println(list.remove(1)); // list.remove(int index)???????????????????????????
    }

    @Test
    void test5() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        for (Integer integer : list) {
            System.out.println(integer);
        }
        System.out.println(list.remove(1));
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }

    /**
     * ????????????
     */
    @Test
    void test6() {
        Staff staff = new Staff();
        staff.setCode("staff_6");
        staff.setName("harden");
        staff.setPassword("010101");
        System.out.println("????????????" + this.staffService.add(staff).getMessage());
    }

    /**
     * ????????????
     */
    @Test
    void test7() {
        Staff staff = new Staff();
//		staff.setId(6);
        staff.setPassword("0000");
        staff.setRemark("??????");
        System.out.println("????????????" + this.staffService.updateById(staff));
    }

    /**
     * ??????????????????
     */
    @Test
    void test8() {
        System.out.println("????????????" + this.staffService.deleteById(5));
    }

    /**
     * ????????????
     */
    @Test
    void test9() {
        // ??????????????????
        QueryWrapper<Staff> wrapper = new QueryWrapper();
        wrapper.like("name", "%n%");
        // ???????????????????????????
        Page<Staff> page = new Page<>(1, 10);
        Page<Staff> staffPage = this.staffMapper.selectPage(page, wrapper);
        System.out.println("?????????" + staffPage.getPages());
        System.out.println("????????????" + staffPage.getTotal());
        List<Staff> records = staffPage.getRecords();
        records.forEach(System.out::println);
    }

    @Test
    void test13() {
        Staff staff = new Staff();
        staff.setId(12);
//		System.out.println(this.staffService.getById(12));
        System.out.println(this.staffMapper.selectById(12));
    }

    @Test
    void test14() {
        QueryWrapper<RoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", 1);
        System.out.println("????????????" + this.roleMenuMapper.delete(wrapper));
        if (this.roleMenuService.remove(wrapper)) {
            System.out.println("???????????????");
        } else {
            System.out.println("???????????????");
        }
    }

    @Test
    void test15() {
        RoleMenu roleMenu = new RoleMenu();
        roleMenu.setRoleId(2);
        roleMenu.setMenuId(3);
        roleMenu.setStatus(1);
        QueryWrapper<RoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", 2).eq("menu_id", 3);
        this.roleMenuService.saveOrUpdate(roleMenu, wrapper);
    }


    @Test
    void test16() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        this.menuService.getStaffMenuPlus(1);
        stopWatch.stop();
        // ??????????????????????????????
        System.out.printf("???????????????%d ??????.%n", stopWatch.getTotalTimeMillis());
        // ??????????????????????????????
        System.out.printf("???????????????%d ??????.%n", stopWatch.getTotalTimeNanos());
    }

    @Test
    void test17() {
        System.out.println("md5:" + MD5Util.MD5("12345"));
        System.out.println("md55:" + MD5Util.MD55("12345"));
        System.out.println(MD5Util.MD55("12345").length());
    }


    @Test
    void test19() {
        System.out.println("????????????" + BusinessStatusEnum.SUCCESS);
    }

    @Test
    void test21() {
        IPage<StaffInsuranceVO> config = new Page<>(1, 10);
        IPage<StaffInsuranceVO> staffInsuranceVOIPage = this.insuranceMapper.listStaffInsuranceVO(config, null);
        System.out.println(staffInsuranceVOIPage.getRecords());
    }

    @Test
    void test22() {
        Staff staff = this.staffService.getById(8);
    }

    @Test
    void test23() {
        IPage<DeptWorkTimeVO> config = new Page<>(1, 20);
        IPage<DeptWorkTimeVO> deptWorkTimeVOIPage = this.deptMapper.listParentDeptWorkTimeVO(config, "");
        System.out.println(deptWorkTimeVOIPage.getRecords());
    }

    @Test
    void test24() {
        WorkTime workTime = this.workTimeService.getById(1);
        System.out.println(workTime.getMorStartTime().getTime() + "  " + workTime.getMorStartTime());
        System.out.println(workTime.getMorEndTime().getTime() + "  " + workTime.getMorEndTime());
        float hour_diff = (float) ((workTime.getMorEndTime().getTime() - workTime.getMorStartTime().getTime()) / (1000 * 60 * 60.0));
        System.out.println("????????????" + hour_diff);
        BigDecimal diff = BigDecimal.valueOf((workTime.getMorEndTime().getTime() - workTime.getMorStartTime().getTime()) / (1000 * 60 * 60.0));
        System.out.println("????????????" + diff);
        System.out.println(workTime.getMorStartTime().getTime());
        System.out.println(workTime.getMorEndTime().getTime());
        System.out.println(workTime.getAftStartTime().getTime());
        System.out.println(workTime.getAftEndTime().getTime());
    }


    @Test
    void test25() {
        WorkTime workTime = this.workTimeService.getById(1);
        System.out.println(workTime.getDeleteFlag());
    }


    @Test
    void test26() throws FileNotFoundException {
        File file = new File("C:\\Users\\asus\\Desktop\\???????????????.xlsx");
        InputStream inputStream = new FileInputStream(file);
        List<Attendance> list = HutoolExcelUtil.readExcel(inputStream, 1, Attendance.class);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
        for (Attendance item : list) {
            System.out.println(dateFormat.format(item.getAttendanceDate()));
        }
    }


    @Test
    void test27() {
        for (String s : DatetimeUtil.getMonthDayList("202203")) {
            System.out.println(s);
        }
    }

    @Test
    void test28() {
        System.out.println(DateUtil.parse("20220301", "yyyyMMdd"));
    }

    @Test
    void test29() {
        QueryWrapper<Salary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("staff_id", 1).eq("month", "202304");
        Salary one = this.salaryService.getOne(queryWrapper);
        if (one == null) {
            System.out.println("??????");
        } else {
            System.out.println("??????");
        }
    }

    @Test
    void test30() {
        System.out.println(new Date());
    }


    @Test
    void test31() {
        System.out.println(DateUtil.thisYear());
    }

    @Test
    void test32() {
        System.out.println(DateUtil.thisMonth());
    }


    @Test
    void test33() {
        System.out.println(DateUtil.beginOfYear(new Date(System.currentTimeMillis())));
    }


    @Test
    void test34() {
        DateTime startDate = DateUtil.beginOfMonth(new Date());
        int length = DateUtil.lengthOfMonth(DateUtil.thisMonth() + 1, DateUtil.isLeapYear(DateUtil.thisYear()));
        System.out.println(DateUtil.thisMonth());
//        DateUtil.thisYear();
    }

    @Test
    void test35() {
        DateTime start = DateUtil.beginOfMonth(new Date());
        DateUtil.offsetDay(start, 1);
        DateTime end = DateUtil.endOfMonth(new Date());
//        System.out.println(start + "--------" + end);
        System.out.println(DateUtil.format(new Date(), "yyyyMM"));
    }


    @Test
    void test36() {
        ResponseDTO departmentData = this.homeService.getDepartmentData();
        System.out.println(departmentData.getData());
    }
    @Test
    void login() {
        String password = MD5Util.MD55("123456");
        StaffDeptVO staffDeptVO = this.staffMapper.findStaffInfo("admin2", password);
        if (staffDeptVO != null) {
            // ??????????????????
            if (staffDeptVO.getStatus() == 1) {
                String token = JWTUtil.generateToken(staffDeptVO.getId(),password);
                System.out.println(Response.success(staffDeptVO, token)); // ?????????????????????token
            }
            System.out.println(Response.error(BusinessStatusEnum.STAFF_STATUS_ERROR));
        }
        throw new RuntimeException("???????????????????????????");
    }

}
