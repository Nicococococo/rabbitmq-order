// 登录、注册、重置密码

function _toggleModal(text) {
	const MNAME = ['loginContent', 'registerContent', 'forgetPwdContent'];
	for (let i = 0, len = MNAME.length; i < len; i++) {
		if (text === MNAME[i]) {
			document.getElementById(text).style.display = 'block'
		} else {
			document.getElementById(MNAME[i]).style.display = 'none'
		}
	}
}
