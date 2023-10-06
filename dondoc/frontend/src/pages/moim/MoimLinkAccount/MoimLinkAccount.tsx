import styles from "./MoimLinkAccount.module.css";
import { useState, useEffect } from 'react'
import { useNavigate, useLocation } from "react-router-dom";
import axios from "axios";
import { useSelector } from "react-redux";
import { UserType } from "../../../store/slice/userSlice";
import { BASE_URL } from "../../../constants";
import { BackLogoHeader } from "../../toolBox/BackLogoHeader/BackLogoHeader";

type linkList = { account: object, index:number, 
  accountId:number,
  accountName:string,
  accountNumber:string,
  accountbalance:number,
  bankCode:number,
  bankName:string}

type selectAccount = {
  accountId:number,
  accountName:string,
  accountNumber:string,
  accountbalance:number,
  bankCode:number,
  bankName:string
}

const defaultAccount = {
  accountId:0,
  accountName:'',
  accountNumber:'',
  accountbalance:0,
  bankCode:0,
  bankName:''
}

function MoimLinkAccount() {
  const [selectAccount, setSelectAccount] = useState<selectAccount>(defaultAccount)
  const [linkList, setLinkList] = useState<linkList[]>([])

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })

  const navigate = useNavigate()
  const { state } = useLocation()
  const moimName = state.moimName
  const moimInfo = state.moimInfo

  const ChangeSelectAccount = (account:selectAccount) => {
    setSelectAccount(account)
  }

  const ToNext = () => {
    if (selectAccount.accountNumber) {
      navigate('/moimselect', { state: { moimName: moimName, moimInfo: moimInfo, account: selectAccount } })
    } else {
      alert('연결 계좌를 선택해주세요')
    }
  }

  const token = userInfo.accessToken
  
  useEffect(() => {
    const fetchData = async () => {
      try {
        const AccountList = await axios.get(`${BASE_URL}/api/account/account/list`, {
          headers: {
            'Content-Type': 'application/json', 
            'Authorization': 'Bearer ' + token
          }
        });
        // console.log('검색결과:', AccountList.data.response)
        setLinkList(AccountList.data.response.accountList)
      }
      catch(err) {
        console.log(err)
      }
    }

    fetchData();
  }, []);
  


  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <BackLogoHeader name="계좌유형"fontSize="2rem" left="5rem" top="0.8rem"/>

        <div className={styles.createcontent}>
          <div className={styles.createment}>
            <h1>연결할 계좌를 선택해주세요.</h1>
          </div>

          <div className={styles.accounts}>
            {linkList && linkList.map((account, index) => (
              <label htmlFor={`account-${index}`} key={index} onClick={() => ChangeSelectAccount(account)}>
                <div className={styles.accountunit}>
                  <div className={styles.banklogo}>
                    <img src={`src/assets/Bank_Logo/${account.bankCode}.svg`} alt="이미지가 없습니다" className={styles.ssafylogo} />
                  </div>
                  <div className={styles.accountinfo}>
                    <p>{`${account.accountName}`}</p>
                    <p className={styles.accountnumber}>{`${account.bankName} ${account.accountNumber}`}</p>
                  </div>
                  <div className={styles.selectcount}>
                    <input 
                      type="radio" 
                      id={`account-${index}`} 
                      checked={account === selectAccount}
                      onChange={() => ChangeSelectAccount(account)} />
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
