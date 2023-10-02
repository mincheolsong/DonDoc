import styles from "./SubMoimUnit.module.css";
import MoimLogo from "../../../../assets/MoimLogo/moimlogo.svg";
import {useEffect, useState} from 'react';
import axios from "axios";
import { useSelector } from "react-redux";
import { UserType } from "../../../../store/slice/userSlice";
import { BASE_URL } from "../../../../constants";

interface Props {
  moimId: number
}

interface moimMembers {
  accountNumber: string,
  bankCode: number,
  moimMemberId: number,
  nickname: string,
  userId: number,
  userType: number,
}

interface moimInfo {
  balance: string,
  identificationNumber: string,
  introduce: string,
  moimAccountNumber: string,
  moimId: number,
  moimMembers: moimMembers[],
  moimName: string,
  moimType: number
}
const defaultMoimInfo = {
  balance: '',
  identificationNumber: '',
  introduce: '',
  moimAccountNumber: '',
  moimId: 0,
  moimMembers: [],
  moimName: '',
  moimType: 0
}

function SubMoimUnit(props: Props) {

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const token = userInfo.accessToken

  const [moimInfo, setMoimInfo] = useState<moimInfo>(defaultMoimInfo)

  useEffect(() => {
    const fetchData = async () => {
      try {
        const res = await axios.get(`${BASE_URL}/api/moim/detail/${props.moimId}`, {
          headers: {
            'Content-Type': 'application/json', 
            'Authorization': 'Bearer ' + token
          }
        });
        if (res.data.response){
          // console.log('모임  조회결과:', res.data.response)
          setMoimInfo(res.data.response)
        } else {
          console.log('없음')
        }
      }
      catch(err) {
        console.log(err)
      }
    }
    fetchData();
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.moimtop}>
        <div className={styles.moimmembers}>
          {moimInfo.moimMembers.length > 0 && moimInfo.moimMembers.map((member, index) => (
              <div className={styles.moimunit} key={index}>
                <p style={{marginTop:'0.2rem', marginBottom:'0', marginLeft: '0.5rem'}}>{member.nickname}</p>
              </div>
            ))}
        </div>
        <div className={styles.moimicon}>
          <div className={styles.iconplace}>
            <img src={MoimLogo} alt="" />
          </div>
        </div>
      </div>
      <div className={styles.moimlower}>
        <div className={styles.moimname}><h1 style={{marginTop:'0.5rem', marginBottom:'0'}}>{moimInfo.moimName}</h1></div>
        <div className={styles.moimaccount}><h3 style={{marginTop:'0.5rem', marginBottom:'0'}}>돈독 {moimInfo.moimAccountNumber}</h3></div>
      </div>
    </div>
  );
}

export default SubMoimUnit;
