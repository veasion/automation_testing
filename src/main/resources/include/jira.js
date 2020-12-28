// 操作 JIRA
// @author luozhuowei

load(env.getPath('/common/proxy.js'));

const jira = evalLoggerProxy('jira');

/**
 * 登录 jira
 */
jira.login = function (username, password) {
    open(env.getString('JIRA_URL'));
    if (!findOne('id=login-form-username')) return;
    type('id=login-form-username', env.getOrDefault('JIRA_USER', username));
    type('id=login-form-password', env.getOrDefault('JIRA_PASS', password));
    click('id=login');
    waitForPageLoaded();
}

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
    type('id=project-field', env.get('JIRA_PROJECT') + env.get('KEY_ENTER'));
    pause(1000);
    click('id=issuetype-field');
    type('id=issuetype-field', env.get('JIRA_ISSUE_CATEGORY') + env.get('KEY_ENTER'));
    click('id=summary');
    type('id=summary', title);
    selectByLabel('id=customfield_10804', env.get('JIRA_ISSUE_SEASON'));
    type('id=description', context);
    selectByLabel('id=customfield_10805', env.get('JIRA_ISSUE_STAGE'));
    selectByLabel('id=customfield_10806', env.get('JIRA_ISSUE_TYPE'));
    click('id=assignee-field');
    type('id=assignee-field', env.get('JIRA_ISSUE_ASSIGNEE'));
    pause(2000);
    type('id=assignee-field', env.get('KEY_ENTER'));
    // 提交
    click('id=create-issue-submit');
    waitForPageLoaded();
}