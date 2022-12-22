import React from 'react';

function Footer(props) {
  return (
    <footer className="bg-[#232629]">
      <div className="flex mx-[auto] w-[1240px] h-[322px] mx-[36.5px] p-[12px] pt-[32px] text-[13px] font-[500]">
        <div className="w-[64px] h-[258px] mb-[32px] bt-[-12px]">
          {/* 로고 이미지 클릭시 홈화면으로 이동 */}
          <a>
            <img
              src="https://cdn-icons-png.flaticon.com/512/2111/2111628.png"
              className="w-[42px] h-[37px]"
            />
          </a>
        </div>

        <nav className="flex h-[278px]">
          <div className="w-[214.89px] pr-[12px] pb-[24px]">
            <h5 className="text-[#BABFC4] mb-[12px] font-[700]">
              STACK OVERFLOW
            </h5>
            <ul className="text-[#9199A1]">
              <li className="h-[25px]">
                <a>Questions</a>
              </li>
              <li className="h-[25px]">
                <a>Help</a>
              </li>
            </ul>
          </div>
          <div className="w-[155.359px] pr-[12px] pb-[24px] ">
            <h5 className="text-[#BABFC4] mb-[12px] font-[700]">PRODUCTS</h5>
            <ul className="text-[#9199A1]">
              <li className="h-[25px]">
                <a>Teams</a>
              </li>
              <li className="h-[25px]">
                <a>Advertising</a>
              </li>
              <li className="h-[25px]">
                <a>Collectives</a>
              </li>
              <li className="h-[25px]">
                <a>Talent</a>
              </li>
            </ul>
          </div>
          <div className="w-[183.312px] pr-[12px] pb-[24px]">
            <h5 className="text-[#BABFC4] mb-[12px] font-[700]">COMPANY</h5>
            <ul className="text-[#9199A1]">
              <li className="h-[25px]">
                <a>About</a>
              </li>
              <li className="h-[25px]">
                <a>Press</a>
              </li>
              <li className="h-[25px]">
                <a>Work Here</a>
              </li>
              <li className="h-[25px]">
                <a>Legal</a>
              </li>
              <li className="h-[25px]">
                <a>Privacy Policy</a>
              </li>
              <li className="h-[25px]">
                <a>Terms of Service</a>
              </li>
              <li className="h-[25px]">
                <a>Contact Us</a>
              </li>
              <li className="h-[25px]">
                <a>Cookie Settings</a>
              </li>
              <li className="h-[25px]">
                <a>Cookie policy</a>
              </li>
            </ul>
          </div>
          <div className="w-[273.344px] h-[254px] pr-[12px] pb-[24px]">
            <h5 className="text-[#BABFC4] font-bold mb-[12px] font-[700]">
              STACK EXCHANGE NETWORK
            </h5>
            <ul className="text-[#9199A1]">
              <li className="h-[25px]">
                <a>Technology</a>
              </li>
              <li className="h-[25px]">
                <a>Culture & recreation</a>
              </li>
              <li className="h-[25px]">
                <a>Life & arts</a>
              </li>
              <li className="h-[25px]">
                <a>Science</a>
              </li>
              <li className="h-[25px]">
                <a>Professional</a>
              </li>
              <li className="h-[25px]">
                <a>Business</a>
              </li>
              <li className="h-[25px]">
                <a>API</a>
              </li>
              <li className="h-[25px]">
                <a>Data</a>
              </li>
            </ul>
          </div>
        </nav>

        <div className="flex flex-col w-[313.094px] h-[278px] text-[11px] text-[#9199A1]">
          <ul className="flex gap-2">
            <li>
              <a>Blog</a>
            </li>
            <li>
              <a>Facebook</a>
            </li>
            <li>
              <a>Twitter</a>
            </li>
            <li>
              <a>LinkedIn</a>
            </li>
            <li>
              <a>instagram</a>
            </li>
          </ul>
          <p className="mt-[188.8px] mb-[24px]">
            Site design / logo © 2022 Stack Exchange Inc; user contributions
            licensed under CC BY-SA. rev 2022.12.21.43127
          </p>
        </div>
      </div>
    </footer>
  );
}

export default Footer;
