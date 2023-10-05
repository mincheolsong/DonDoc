import { useState } from "react";
import styles from "./InviteManagerModal.module.css";
import axios from "axios";
import { BASE_URL } from "../../../../constants";

interface Props {
  setInviteModalOpen(id: boolean): void;
  token: string;
  setManager: (manager: searchUnit) => void;
}

type searchUnit = {
  userId: number,
  phoneNumber: string,
  imageNumber: number,
  bankName: string,
  bankCode: number,
  accountNumber: string,
  msg: string,
  nickName: string
}
const initialSearchResult: searchUnit = {
  userId: 0,
  phoneNumber: "",
  imageNumber: 0,
  bankName: "",
  bankCode: 0,
  accountNumber: "",
  msg: "",
  nickName: ""
};

function InviteManagerModal({setInviteModalOpen, setManager, token}: Props) {

  const [searchInput, setSearchInput] = useState<string>('')
  const [searchResult, setSearchResult] = useState<searchUnit>(initialSearchResult);

  const SelectManager = (manager: searchUnit) => {
    const managerData: Partial<searchUnit> = {
      userId: manager.userId,
      phoneNumber: manager.phoneNumber,
      nickName: manager.nickName,
    };
    setManager(managerData as searchUnit);
    setInviteModalOpen(false);
  };
  
  

  const ChangeSearchInput = (e:React.ChangeEvent<HTMLInputElement>) => {
    setSearchInput(e.target.value)
  }

  const SearchMember = async() => {
    try {
      const res = await axios.get(`${BASE_URL}/api/user/find_user/${searchInput}`, {
        headers: {
          'Content-Type': 'application/json', 
          'Authorization': 'Bearer ' + token
        }
      });
      if (res.data.response) {
        setSearchResult(res.data.response)
      } else {
        // 검색 결과가 없을 때 처리할 로직 추가
        // alert('검색 결과가 없습니다.');
        alert(res.data.error.message)
        setSearchResult(initialSearchResult); // 또는 다른 초기값을 설정할 수 있음
      }
    }catch(err) {
      // console.log(err)
    }
  }

  return (
    <div className={styles.container}>
      <div className={styles.content}>

        <div className={styles.toptab}>
          <h1>매니저 초대</h1>
        </div>

        <div className={styles.maincontent}>

          <div className={styles.searchs}>

            <div className={styles.searchbox}>
              <div className={styles.inputgroup}>
                <div className={styles.inputlabel}>
                  <h2>전화번호</h2>
                </div>
                <div className={styles.searchgroup}>
                  <div className={styles.inputbox}>
                    <input type="text" onChange={ChangeSearchInput} value={searchInput}/>
                  </div>
                  <div className={styles.button}>
                    <button onClick={SearchMember}>검색</button>
                  </div>
                </div>
              </div>
            </div>

            <div className={styles.searchresult}>
              {searchResult && searchResult.accountNumber ? (
                <div className={styles.searchresultunit}>
                  <div className={styles.usercharacter}>
                    <div className={styles.userImg}>
                      <img src={`/src/assets/characterImg/${searchResult.imageNumber}.png`} alt="" />
                    </div>
                  </div>
                  <div className={styles.useraccount}>
                    <h2 style={{marginTop:'0.5rem', marginBottom:'0.5rem'}}>{searchResult.nickName}</h2>
                    {searchResult.accountNumber == "대표계좌가 없습니다." ? (
                      <h3 style={{marginTop:'0.5rem', marginBottom:'0.5rem'}}>{searchResult.phoneNumber}</h3>
                      ):(
                      <h3 style={{marginTop:'0.5rem', marginBottom:'0.5rem'}}>{searchResult.accountNumber}</h3>
                    )}
                  </div>
                  <div className={styles.appendbtn}>
                    <button onClick={() => SelectManager(searchResult)}>선택</button>
                  </div>
                </div>
              ):(
                <></>
              )}
            </div>

          </div>

        </div>
        <div className={styles.btndiv}>
          <button className={styles.closebtn} onClick={() => {setInviteModalOpen(false)}}>닫기</button>
        </div>

      </div>
    </div>
  );
}

export default InviteManagerModal;
