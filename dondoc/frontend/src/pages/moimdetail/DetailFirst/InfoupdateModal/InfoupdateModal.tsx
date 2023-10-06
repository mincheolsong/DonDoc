import React, {useState} from "react";
import styles from "./InfoupdateModal.module.css";
// import axios from "axios";

interface Props {
  setModalOpen(id: boolean) : void
}

// type moimMembers = { memberUnit: object, index:number, 
//   userId:number,
//   moimMemberId:number,
//   userType:number,
//   nickname:string,
//   accountNumber:string,
//   bankCode:string
// }

function InfoupdateModal({setModalOpen}: Props) {
  const [moimInfo, setMoimInfo] = useState<string>('')
  // const [moimMembers, setMoimMembers] = useState<[]>([])

  const InfoChange = (e:React.ChangeEvent<HTMLTextAreaElement>) => {
    setMoimInfo(e.target.value)
    // console.log(moimInfo)
  }

  const ModalClose = () => {
    setModalOpen(false)
  }

  // useEffect(() => {
  //   const fetchData = async () => {

  //     const BASE_URL = 'http://j9d108.p.ssafy.io:9999'
  //     const token = "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoi7KCc7J2065OgIiwidXNlcm5hbWUiOiIwMTAxMTExMjIyMiIsInN1YiI6IjEiLCJpYXQiOjE2OTU3MzczMDIsImV4cCI6MTY5NTczOTEwMn0.PWlFV71TuxZwfy3YFsY-FSBaz6Y2C_iROj0MLFAiTT8"
  //     const moimId = 1

  //     try {
  //       const res = await axios.get(`${BASE_URL}/api/moim/detail/${moimId}`, {
  //         headers: {
  //           'Content-Type': 'application/json', 
  //           'Authorization': 'Bearer ' + token
  //         }
  //       });
  //       console.log('검색결과:', res.data.response)
  //       setMoimMembers(res.data.response)
  //     }
  //     catch(err) {
  //       console.log(err)
  //     }
  //   }

  //   fetchData();
  // }, []);

  return (
    <div className={styles.container}>
      <div className={styles.content}>

        <div className={styles.toptab}>
          <h1>올해는 다이어트 성공</h1>
        </div>

        <div className={styles.maincontent}>
          <div className={styles.contentbox}>

            <div className={styles.moimintro}>
              <div className={styles.introtitle}>
                <h2>모임 소개</h2>
              </div>
              <div className={styles.introcontent}>
                <textarea className={styles.introinput} onChange={InfoChange}></textarea>
              </div>
            </div>

            <div className={styles.moimmembers}>
              <div className={styles.introtitle}>
                <h2>멤버</h2>
              </div>
              <div className={styles.memberscontent}>

              </div>
            </div>

          </div>
        </div>

        <div className={styles.infobtns}>
            <button onClick={ModalClose}>닫기</button>
            <button>수정하기</button>
        </div>

      </div>
    </div>
  )
}

export default InfoupdateModal;
