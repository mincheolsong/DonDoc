import styles from "./SelectAccount.module.css";
import { useState, useEffect } from 'react'
import axios from "axios";
import { useSelector } from "react-redux";
import { UserType } from "../../../../store/slice/userSlice";
import { BASE_URL } from "../../../../constants";

interface Props {
  setSelectAccountOpen(id: boolean) : void;
  setSelectedAccount : (id: selectedAccountType) => void;
  selectedAccount : selectedAccountType; // 타입 추가
}

type selectedAccountType = {
  accountId: number;
  accountName: string;
  accountNumber: string;
  balance: number;
  bankCode: number;
  bankName: string;
}

type linkList = { account: object, index:number, 
  accountId:number,
  accountName:string,
  accountNumber:string,
  balance:number,
  bankCode:number,
  bankName:string}


function SelectAccount({setSelectAccountOpen, setSelectedAccount, selectedAccount}: Props) {

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const token = userInfo.accessToken

  const [linkList, setLinkList] = useState<linkList[]>([])

  const SelectAccountClose = () => {
    setSelectAccountOpen(false)
  }

  const ChangeSelectAccount = (account:selectedAccountType) => {
    setSelectedAccount(account)
  }

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
        // console.log('선택된 계좌', setSelectedAccount)
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

        <div className={styles.toptab}>
          <h1>내가 연결할 계좌 선택</h1>
        </div>

        <div className={styles.maincontent}>
          <div className={styles.contentbox}>

          <div className={styles.accounts}>
            {linkList && linkList.map((account, index) => (
              <label htmlFor={`account-${index}`} key={index} onClick={() => ChangeSelectAccount(account)}>
                <div className={styles.accountunit}>
                  <div className={styles.banklogo}>
                    <img src={`src/assets/Bank_Logo/${account.bankCode}.svg`} alt="" className={styles.ssafylogo} />
                  </div>
                  <div className={styles.accountinfo}>
                    <p>{`${account.accountName}`}</p>
                    <p className={styles.accountnumber}>{`${account.bankName} ${account.accountNumber}`}</p>
                  </div>
                  <div className={styles.selectcount}>
                    <input 
                      type="radio" 
                      id={`account-${index}`} 
                      checked={account.accountNumber === selectedAccount.accountNumber}
                      onChange={() => ChangeSelectAccount(account)} />
                  </div>
                </div>
              </label>
            ))}
          </div>


          </div>
        </div>

        <div className={styles.infobtns}>
            <button onClick={SelectAccountClose}>닫기</button>

        </div>

      </div>
    </div>
  );
}

export default SelectAccount;
