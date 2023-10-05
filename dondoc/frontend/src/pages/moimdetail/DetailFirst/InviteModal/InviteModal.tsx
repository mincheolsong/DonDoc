import React, {useState, useEffect} from "react";
import styles from "./InviteModal.module.css";
import axios from "axios";
import { useSelector } from "react-redux";
import { UserType } from "../../../../store/slice/userSlice";
// import MemberUnit from "./MemberUnit/MemberUnit";
import { BASE_URL } from "../../../../constants";

interface Props {
  setModalOpen(id: boolean) : void,
  moimIdNumber: string
}

type friendList = { friend: inviteUnit,
  id:number,
  name:string,
  imageNumber:string,
  phoneNumber:string,
  bankName:string,
  bankCode:number,
  accountNumber:string,
  userId:number}
type inviteUnit = {
  id:number,
  name:string,
  imageNumber:string,
  phoneNumber:string,
  bankName:string,
  bankCode:number,
  accountNumber:string,
  userId:number
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
type newInviteUnit = {
  userId: number
}
type newInviteList = {
  userId: number
}


function InviteModal({setModalOpen, moimIdNumber}: Props) {

  const userInfo:UserType = useSelector((state:{user:UserType})=>{
    return state.user
  })
  const token = userInfo.accessToken
  const moimId = parseInt(moimIdNumber, 10);

  const [searchInput, setSearchInput] = useState<string>('')
  const [searchResult, setSearchResult] = useState<searchUnit>(initialSearchResult);
  const [friendList, setFriendList] = useState<friendList[]>([])
  const [inviteList, setInviteList] = useState<newInviteList[]>([])

  const ModalClose = () => {
    setModalOpen(false)
  }

  const ChangeSearchInput = (e:React.ChangeEvent<HTMLInputElement>) => {
    setSearchInput(e.target.value)
  }
  
  const AppendInviteList = (friend: inviteUnit) => {
    const isAlreadyAdded = inviteList.some((item) => item.userId === friend.userId);

    if (!isAlreadyAdded) {
      const newInviteUnit: newInviteUnit = {
        "userId": friend.userId,
      };

      const newInviteList = [...inviteList, newInviteUnit];
      setInviteList(newInviteList);
    }
  };

  const AppendSearchUnit = (unit: searchUnit) => {
    if (searchResult && searchResult.accountNumber) {
      const isAlreadyAdded = inviteList.some((item) => item.userId === unit.userId);
  
      if (!isAlreadyAdded) {
        const newInviteUnit: newInviteUnit = {
          "userId": unit.userId,
        };
  
        const newInviteList = [...inviteList, newInviteUnit];
        setInviteList(newInviteList);
      }
    }
  };

  const SearchMember = async() => {
    try {
      const res = await axios.get(`${BASE_URL}/api/user/find_user/${searchInput}`, {
        headers: {
          'Content-Type': 'application/json', 
          'Authorization': 'Bearer ' + token
        }
      });
      if (res.data.response) {
        console.log(res.data.response)
        setSearchResult(res.data.response)
      } else {
        // 검색 결과가 없을 때 처리할 로직 추가
        console.log('검색 결과가 없습니다.');
        setSearchResult(initialSearchResult); // 또는 다른 초기값을 설정할 수 있음
      }
    }catch(err) {
      console.log(err)
    }
  }

  useEffect(() => {
    const fetchData = async () => {

      
      try {
        const res = await axios.get(`${BASE_URL}/api/friend/list`, {
          headers: {
            'Content-Type': 'application/json', 
            'Authorization': 'Bearer ' + token
          }
        });
        console.log('친구리스트:', res.data.response.list)
        setFriendList(res.data.response.list)
      }
      catch(err) {
        console.log(err)
      }
    }

    fetchData();
  }, []);

  const InviteMoimFriend = async() => {
    const data = {
      "moimId" : moimId,
      "invite" : inviteList
    }
    try {
      const response = await axios.post(`${BASE_URL}/api/moim/invite`, data, {
        headers: {
          'Content-Type': 'application/json', 
          'Authorization': 'Bearer ' + token
        }
      });
      // console.log(data)
      // console.log(response)
      if (response.data.success == true) {
        alert('초대에 성공하였습니다.')
        setModalOpen(false)
      }
    } catch(error) {
      console.log('error:', error)
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

        <div className={styles.maincontent}>
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
          
          <div className={styles.searchresult} onClick={() => AppendSearchUnit(searchResult)}>
          {searchResult && searchResult.accountNumber ? (
            <div
              className={`${styles.searchresultunit} ${
                inviteList.some(item => item.userId === searchResult.userId) ? styles.added : ''
              }`}
            >
              <div className={styles.usercharacter}>
                <div className={styles.userImg}>
                  <img src={`/src/assets/characterImg/${searchResult.imageNumber}.png`} alt="" />
                </div>
              </div>
              <div className={styles.useraccount}>
                <h2 style={{marginTop:'0.5rem', marginBottom:'0.5rem'}}>{searchResult.nickName}</h2>
                {searchResult.accountNumber === "대표계좌가 없습니다." ? (
                  <h3 style={{marginTop:'0.5rem', marginBottom:'0.5rem'}}>{searchResult.phoneNumber}</h3>
                ) : (
                  <h3 style={{marginTop:'0.5rem', marginBottom:'0.5rem'}}>{searchResult.accountNumber}</h3>
                )}
              </div>
              <div className={styles.appendbtn}>
                <button>추가</button>
              </div>
            </div>
          ) : (
            <></>
          )}

          </div>

          <div className={styles.friendlist}>
            <div className={styles.friendcontent}>  
              <div className={styles.listlabel}>
                <h2>친구 리스트</h2>
              </div>
              <div className={styles.friendbox}>
              {friendList && friendList.map((friend, index) => (
                <div
                  className={`${styles.myfriendunit} ${inviteList.some(item => item.userId === friend.userId) ? styles.added : ''}`}
                  onClick={() => AppendInviteList(friend)}
                  key={index}
                >
                  <div className={styles.usercharacter}>
                    <div className={styles.userImg}>
                      <img src={`/src/assets/characterImg/${friend.imageNumber}.png`} alt="" />
                    </div>
                  </div>
                  <div className={styles.useraccount}>
                    <h2 style={{marginTop:'0.5rem', marginBottom:'0.5rem'}}>{friend.name}</h2>
                    <h3 style={{marginTop:'0.5rem', marginBottom:'0.5rem'}}>{friend.phoneNumber}</h3>
                  </div>
                  <div className={styles.appendbtn}>
                    <button>추가</button>
                  </div>
                </div>
              ))}

              </div>
            </div>
          </div>

        </div>

        <div className={styles.infobtns}>
          <button onClick={ModalClose}>닫기</button>
          <button onClick={InviteMoimFriend}>초대하기</button>
        </div>

      </div>
    </div>
  );
}

export default InviteModal;
