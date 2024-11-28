// 动态导入所有图标
import * as Icons from '@ant-design/icons-vue';

// 常用图标名称列表
const commonIconNames = [
    // 基础图标
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

    // 社交和媒体相关
    'InstagramOutlined',
    'FacebookOutlined',
    'TwitterOutlined',
    'LinkedinOutlined',
    'YoutubeOutlined',
    'GitlabOutlined',
    'GithubOutlined',
    'WechatOutlined',
    'AlipayOutlined',

    // 设备相关
    'AndroidOutlined',
    'AppleOutlined',
    'WindowsOutlined',
    'LaptopOutlined',
    'MobileOutlined',
    'TabletOutlined',
    'CameraOutlined',

    // 文件和文档
    'FolderOutlined',
    'FileAddOutlined',
    'FileSearchOutlined',
    'FileTextOutlined',
    'FileExcelOutlined',
    'FilePdfOutlined',

    // 操作和工具
    'EditOutlined',
    'DeleteOutlined',
    'SaveOutlined',
    'CopyOutlined',
    'UndoOutlined',
    'RedoOutlined',
    'ShareAltOutlined',

    // 导航图标
    'ArrowLeftOutlined',
    'ArrowRightOutlined',
    'ArrowUpOutlined',
    'ArrowDownOutlined',
    'UpOutlined',
    'DownOutlined',
    'LeftOutlined',
    'RightOutlined',

    // 系统和安全
    'LockOutlined',
    'SecurityScanOutlined',
    'EyeOutlined',
    'EyeInvisibleOutlined',
    'KeyOutlined',

    // 状态和提示
    'CheckCircleOutlined',
    'WarningOutlined',
    'InfoCircleOutlined',
    'ExclamationCircleOutlined',
    'CloseCircleOutlined',
    'CheckOutlined',
    'CloseOutlined',
    'MinusOutlined',
    'PlusOutlined',

    // 购物和支付
    'ShoppingCartOutlined',
    'CreditCardOutlined',
    'DollarOutlined',
    'EuroOutlined',
    'MoneyCollectOutlined',

    // 娱乐与社交
    'PlayCircleOutlined',
    'PauseCircleOutlined',
    'StopOutlined',
    'VideoCameraOutlined',
    'CameraOutlined',
    'SoundOutlined',
    'AppstoreAddOutlined',

    // 其他常用图标
    'CloudOutlined',
    'CloudDownloadOutlined',
    'CloudUploadOutlined',
    'GlobalOutlined',
    'BarsOutlined',
    'AlignLeftOutlined',
    'AlignRightOutlined',
    'AlignCenterOutlined',
    'TeamOutlined',
    'ProjectOutlined',
    'BankOutlined',
    'FundOutlined',
    'FieldTimeOutlined',
    'BranchOutlined',
    'PushpinOutlined'
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
