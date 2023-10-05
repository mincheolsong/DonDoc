import styles from "./DetailMain.module.css";
import DetailFirst from "../DetailFirst/DetailFirst"
import DetailSecond from "../DetailSecond/DetailSecond"
import DetailThird from "../DetailThird/DetailThird"
import { useLocation, useParams } from "react-router-dom";
import axios from "axios";
import { useSwipeable } from 'react-swipeable';
import { BASE_URL } from "../../../constants";
import { useSelector } from "react-redux";
import { UserType } from "../../../store/slice/userSlice";
import { useEffect, useState } from 'react'
import BackLogoHeader from "../../toolBox/BackLogoHeader/BackLogoHeader";

type Member = {
  accountNumber: string,
  bankCode: number,
  bankName: string,
  moimMemberId: number,
  nickname: string,
  phoneNumber: string,
  userId:number,
  userImageNumber:number,
  userType: number
}

function DetailMain() {

  const { moimId } = useParams();
  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const token = userInfo.accessToken

  const { state } = useLocation()
  const userType = state.userType
  const accountId = state.accountId

  const [moimName, setMoimName] = useState<string>('')
  const [moimType, setMoimType] = useState<number>(0)
  const [moimMemberID, setmoimMemberId] = useState<number>(0)
  const [moimMembers, setmoimMembers] = useState<Member[]>([])
  const [moimAccountNum, setmoimAccountNum] = useState<string>('')
  const [moimIdNum, setmoimIdNum] = useState<string>('')
  const [MemAccount, setMemAccount] = useState<string>('')


  useEffect(() => {
    axios.get(`${BASE_URL}/api/moim/detail/${moimId}`,{
      headers:{
        'Authorization': 'Bearer ' + token
      }
    })
    .then((res) => {
      console.log(res.data.response)
      setmoimMembers(res.data.response.moimMembers)
      setmoimAccountNum(res.data.response.moimAccountNumber)
      setmoimIdNum(res.data.response.identificationNumber)
      const members = res.data.response.moimMembers
      members.map((mem:Member) => {
        if (mem.nickname === userInfo.nickname) {
          setmoimMemberId(mem.moimMemberId)
          setMemAccount(mem.accountNumber)
        }
      })

    })
    .catch((err) => {
      console.log(err)
    })
  },[])



  useEffect(() => {
    const fetchData = async () => {
      try {
        // moim/detail/${moimId}
        const res = await axios.get(`${BASE_URL}/api/moim/detail/${moimId}`, {
          headers: {
            'Content-Type': 'application/json', 
            'Authorization': 'Bearer ' + token
          }
        });
        console.log('모임 멤버:', res.data.response.moimType)
        setMoimName(res.data.response.moimName)
        setMoimType(res.data.response.moimType)
      }
      catch(err) {
        console.log(err)
      }
    }
    fetchData();
  }, []);

  const [activeComponentIndex, setActiveComponentIndex] = useState(0);

  const handlers = useSwipeable({
    onSwipedLeft: () => handleSwipe(-100),
    onSwipedRight: () => handleSwipe(100),
    trackMouse: true,
  });

  const handleSwipe = (deltaX: number) => {
    if (deltaX > 50 && activeComponentIndex > 0) {
      setActiveComponentIndex(activeComponentIndex - 1);
    } else if (deltaX < -50 && activeComponentIndex < 2) {
      setActiveComponentIndex(activeComponentIndex + 1);
    }
  };


  return (
    <div className={styles.container}>
      <BackLogoHeader name={moimName} fontSize="2rem" left="5rem" top="0.8rem" />
      <div className={styles.pages} {...handlers}>
        {activeComponentIndex === 0 && (
          <DetailFirst userType={userType} accountId={accountId} moimId={moimId} />
        )}
        {activeComponentIndex === 1 && (
          <DetailSecond moimId={moimId} memberType={userType} moimType={moimType}/>
        )}
        {activeComponentIndex === 2 && <DetailThird moimId={moimId} memberType={userType}
        members = {moimMembers} moimMemberId={moimMemberID}
        moimAccountNum = {moimAccountNum} moimIdNum = {moimIdNum}
        memAccount = {MemAccount}/>}
        
      </div>
    </div>
  );
}

export default DetailMain;
