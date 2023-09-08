// import React from 'react'
import styles from './Home.module.css'
import { Link } from "react-router-dom";

function Home() {
  return (
    <div className={styles.container}>
      <div className={styles.content}>

        <div className={styles.contentbanner}>
          <div className={styles.title}>DD Bank</div>
          <div className={styles.information}>DD Bank에서 제공하는 서비스 입니다</div>
        </div>

        <div className={styles.contentbox}>
          <div className={styles.links}>
            <Link className={styles.linktext} to="/account">계좌 개설</Link><br />
            <Link className={styles.linktext} to="/account-list">계좌 목록 조회</Link><br />
            <Link className={styles.linktext} to="/account-detail">계좌 상세 조회</Link><br />
            <Link className={styles.linktext} to="/account-trans">계좌 거래 내역 조회</Link><br />
            <Link className={styles.linktext} to="/account-trans-detail">상세 거래 내역 조회</Link><br />
            <Link className={styles.linktext} to="/account-name">계좌 실명 조회</Link><br />
            <Link className={styles.linktext} to="/account-transfer">계좌 이체</Link><br />
            <Link className={styles.linktext} to="/account-master">예금주 생성</Link><br />
          </div>
        </div>

      </div>


    </div>
  )
}

export default Home