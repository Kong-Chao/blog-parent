// icons.js

// 动态导入所有图标
import * as Icons from '@ant-design/icons-vue';

// 常用图标名称列表
const commonIconNames = [
    'HomeOutlined',
    'UserOutlined',
    'SettingOutlined',
    'DashboardOutlined',
    'UsergroupAddOutlined',
    'LockOutlined',
    'BellOutlined',
    'MailOutlined',
    'AppstoreOutlined',
    'CalendarOutlined',
    'EditOutlined',
    'FileOutlined',
    'PieChartOutlined',
    'SearchOutlined',
    'ShoppingCartOutlined',
    'TableOutlined',
    'ToolOutlined',
    'WalletOutlined',
    'RadarChartOutlined',
    'MonitorOutlined',
    'ToolOutlined'
    // ...更多常用图标可以在此添加
];

// 创建一个包含常用图标的映射对象
const iconMap = {};

// 遍历所有图标并创建映射
for (const [key, component] of Object.entries(Icons)) {
    if (commonIconNames.includes(key)) {
        iconMap[key] = component;
    }
}

// 图标选项数组（用于选择图标的下拉列表）
const iconOptions = Object.entries(iconMap).map(([key, component]) => ({
    label: key.replace(/Outlined$/, ''), // 去掉图标名称的 "Outlined" 后缀
    value: key,
    component: component
}));

// 导出图标映射和选项列表
export { iconMap, iconOptions };