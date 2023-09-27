import styles from "./CallAccount.module.css";
import BackLogoHeader from "../../toolBox/BackLogoHeader";
import { useState,useEffect } from "react"
import { moim } from "../../../api/api";
import {  useSelector } from "react-redux/es/hooks/useSelector";
import { UserType } from "../../../store/slice/userSlice";
import { Account } from "../../../store/slice/userSlice";
import CallAcountItem from "../CallAcountItem";


function CallAccount() {
  const [checkList,setCheckList] = useState<string[]>([]);
  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const [allAccount,setAllAccount] = useState<Account[]>([])


  useEffect(()=>{
    moim.get("/api/account/account/list/bank",{headers: {
      Authorization: `Bearer ${userInfo.accessToken}`
    }})
    .then((response)=>{
      console.log(response.data.response)
      const Data = response.data.response.accountList
      const Account = Data.map((account)=>{
        return account = {...account, isCheck:false}
      })
      setAllAccount(Account)
      console.log(allAccount)
    })
    .catch((err)=>{
      console.log(err)
    })
  },[])


  return (
    <div >
      <BackLogoHeader name="계좌 불러오기" left="6rem" top="0.9rem" fontSize="1.8rem" />
        <div className={styles.mainContainer}>
         {/* 여기에 맵함수 */}
         {allAccount.map((account)=>{
          return <CallAcountItem checked={true} bankCode={account.bankCode} accountName={account.accountName} bankName={account.bankName} accountNumber={account.accountNumber} />
         })}
          
          {/* 여기에 맵함수 */}
        </div>
    </div>
  );
}

export default CallAccount;

