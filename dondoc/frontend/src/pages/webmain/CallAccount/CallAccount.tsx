import styles from "./CallAccount.module.css";
import BackLogoHeader from "../../toolBox/BackLogoHeader";
import { useState,useEffect } from "react"
import { moim } from "../../../api/api";
import {  useSelector } from "react-redux/es/hooks/useSelector";
import { CheckAccount, UserType } from "../../../store/slice/userSlice";
import CallAcountItem from "../CallAcountItem";


function CallAccount() {
 
  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const [allAccount,setAllAccount] = useState<CheckAccount[]>([])
  const [isLoading,setIsLoading]=useState<boolean>(true)

  useEffect(()=>{
    moim.get("/api/account/account/list/bank",{headers: {
      Authorization: `Bearer ${userInfo.accessToken}`
    }})
    .then((response)=>{
      console.log(response)
      const Data = response.data.response.accountList
      const Account = Data.map((account:CheckAccount)=>{
        account.isCheck = false
        return account
      })
      setAllAccount(Account)
      console.log(Account)
    })
    .catch((err)=>{
      console.log(err)
    }).finally(()=>{
      setIsLoading(false)
    })
  },[])


  return (
    <div >
      <BackLogoHeader name="계좌 불러오기" left="6rem" top="0.9rem" fontSize="1.8rem" />
     {isLoading ?"" : <CallAcountItem account={allAccount} />  }
     
     
    </div>
  );
}

export default CallAccount;

