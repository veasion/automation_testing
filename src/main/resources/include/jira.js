// 操作 JIRA
// @author luozhuowei

load(env.getPath('/common/proxy.js'));

const jira = evalLoggerProxy('jira');

jira.configs = env.getOrDefault('JIRA', {});

/**
 * 登录 jira
 */
jira.login = function (username, password) {
    open(jira.configs.url);
    if (!findOne('id=login-form-username')) return;
    type('id=login-form-username', username || jira.configs.username);
    type('id=login-form-password', password || jira.configs.password);
    click('id=login');
    waitForPageLoaded();
};

/**
 * 创建问题
 */
jira.createIssue = function (title, context) {
    env.put('COMMAND_INTERVAL', 200);
    jira.login();
    click('id=create_link');
    waitForElementDisplayed('id=project-field');
    // 开始填写
    click('id=project-field');
    type('id=project-field', jira.configs['issueProject'] + env.get('KEY_ENTER'));
    pause(1000);
    click('id=issuetype-field');
    type('id=issuetype-field', jira.configs['issueCategory'] + env.get('KEY_ENTER'));
    click('id=summary');
    type('id=summary', title);
    selectByLabel('id=customfield_10804', jira.configs['issueSeason']);
    type('id=description', context);
    selectByLabel('id=customfield_10805', jira.configs['issueStage']);
    selectByLabel('id=customfield_10806', jira.configs['issueType']);
    click('id=assignee-field');
    type('id=assignee-field', jira.configs['issueAssignee']);
    pause(2000);
    type('id=assignee-field', env.get('KEY_ENTER'));
    // 提交
    click('id=create-issue-submit');
    waitForPageLoaded();
};