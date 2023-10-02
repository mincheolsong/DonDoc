import styles from "./ChargeMoimAccount.module.css";
import React, { useState } from "react"
import axios from "axios";
import { BASE_URL } from "../../../../constants";
import { UserType } from "../../../../store/slice/userSlice";
import { useSelector } from "react-redux";

interface Props {
  setModalOpen(id: boolean) : void,
  accountId: number,
  toAccount: string
}

function ChargeMoimAccount(props: Props) {

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const token = userInfo.accessToken

  const [password, setPassword] = useState<string>('')
  const [mySign, setMySign] = useState<string>('')
  const [yourSign, setYourSign] = useState<string>('')
  const [amount, setAmount] = useState<number>(0)

  const ModalClose = () => {
    props.setModalOpen(false)
  }

  const ChangePassword = (e:React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value)
  }
  const ChangeMySign = (e:React.ChangeEvent<HTMLInputElement>) => {
    setMySign(e.target.value)
  }
  const ChangeYourSign = (e:React.ChangeEvent<HTMLInputElement>) => {
    setYourSign(e.target.value)
  }
  const ChangeAmount = (e:React.ChangeEvent<HTMLInputElement>) => {
    const newValue = e.target.value !== '' ? parseInt(e.target.value, 10) : 0;
    setAmount(newValue)
  }

  const WatchSome = () => {
    // console.log(props.accountId)
    console.log(data)
  }

  const data = {
    "accountId": props.accountId,
    "password": password,
    "sign": mySign,
    "toAccount": props.toAccount,
    "toCode": 108,
    "toSign": yourSign,
    "transferAmount": amount
  }

  const AccountTransfer = async() => {
    try {
      const res = await axios.post(`${BASE_URL}/api/account/account/transfer`, data, {
        headers: {
          'Content-Type': 'application/json', 
          'Authorization': 'Bearer ' + token
        }
      });
      if (res.data.response) {
        console.log(res.data.response)
        props.setModalOpen(false)
      } else {
        // 검색 결과가 없을 때 처리할 로직 추가
        console.log(res);
      }
    }catch(err) {
      console.log(err)
    }
  }


  return (
    <div className={styles.container}>
      <div className={styles.content}>

        <div className={styles.toptab}>
          <div className={styles.requesttext}>
            <p style={{color: '#7677E8', borderBottom: '4px solid #7677E8'}}>초대하기</p>
          </div>
        </div>

        <div className={styles.inputgroup}>
          <div className={styles.inputs}>
            <div className={styles.inputbox}>
              <h3>패스워드</h3>
              <input type="password" onChange={ChangePassword} value={password}/>
            </div>
            <div className={styles.inputbox}>
              <h3>내 통장에 남기기</h3>
              <input type="text" onChange={ChangeMySign} value={mySign}/>
            </div>
            <div className={styles.inputbox}>
              <h3>상대방에 메시지 남기기</h3>
              <input type="text" onChange={ChangeYourSign} value={yourSign}/>
            </div>
            <div className={styles.inputbox}>
              <h3>금액</h3>
              <input type="text" onChange={ChangeAmount} value={amount}/>
            </div>
          </div>
        </div>

        <div className={styles.infobtns}>
          <button onClick={WatchSome}>aaa</button>
          <button onClick={ModalClose}>닫기</button>
          <button onClick={AccountTransfer}>충전하기</button>
        </div>

      </div>
    </div>
  );
}

export default ChargeMoimAccount;
