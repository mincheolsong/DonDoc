import styles from "./MoimLinkAccount.module.css";
import ssafylogo from '../../../assets/ssafy_logo.png'
import { useState } from 'react'
import { useNavigate, useLocation } from "react-router-dom";

const datas = [
  {
    'bank': '하나은행',
    'name': '영플러스통장',
    'accountnumber': '237-128127-12478'
  },
  {
    'bank': '대구은행',
    'name': '영마이너스통장',
    'accountnumber': '237-131131-34678'
  },
  {
    'bank': '우리은행',
    'name': '영앰지통장',
    'accountnumber': '010-124527-12458'
  }
];

function MoimLinkAccount() {
  const [selectAccount, setSelectAccount] = useState<string>('')

  const navigate = useNavigate()
  const { state } = useLocation()
  const moimName = state.moimName
  const moimInfo = state.moimInfo

  const ChangeSelectAccount = (accountName) => {
    setSelectAccount(accountName)
  }

  const ToBack = () => {
    navigate(-1)
  }

  const ToNext = () => {
    if (selectAccount) {
      navigate('/moimselect', { state: { moimName: moimName, moimInfo: moimInfo, account: selectAccount } })
    } else {
      console.log('선택해주세요')
    }
  }

  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <div className={styles.topbar}>
          <div className={styles.backbutton}>
            <button className={styles.toback} onClick={ToBack}>
              back
            </button>
          </div>
          <div className={styles.pagename}>
            <h3>모임통장 생성</h3>
          </div>
        </div>

        <div className={styles.createcontent}>
          <div className={styles.createment}>
            <h1>연결할 계좌를 선택해주세요.</h1>
          </div>

          <div className={styles.accounts}>
            {datas.map((account, index) => (
              <label htmlFor={`account-${index}`} key={index} onClick={() => ChangeSelectAccount(account.name)}>
                <div className={styles.accountunit}>
                  <div className={styles.banklogo}>
                    <img src={ssafylogo} alt="" className={styles.ssafylogo} />
                  </div>
                  <div className={styles.accountinfo}>
                    <p>{`${account.bank} ${account.name}`}</p>
                    <p className={styles.accountnumber}>{`${account.bank} ${account.accountnumber}`}</p>
                  </div>
                  <div className={styles.selectcount}>
                    <input type="radio" id={`account-${index}`} checked={account.name === selectAccount} />
                  </div>
                </div>
              </label>
            ))}
          </div>

          <div className={styles.buttondiv}>
            <button className={styles.submitbtn} onClick={ToNext}>다음</button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default MoimLinkAccount;
