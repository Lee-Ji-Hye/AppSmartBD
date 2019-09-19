pragma solidity >=0.4.23;

contract RoomContract {

    // 임차인 정보
    struct Buyer {
        address buyAddress;     // 임차인 계정주소
        bytes32 name;           // 임차인 이름
        bytes32 businessNumber; // 임차인 주민번호/사업자번호
    }

    // 임차인 정보 : 매물의 id를 키값으로 하면, value값으로 임차인의 정보를 불러오는 구조이다.
    mapping (uint => Buyer) public buyerInfo;

    // 1. 컨트랙 소유자 설정
    // 상태변수에 public을 붙이면 자동적으로 owner 변수의 getter 함수가 생성된다.
    address payable public owner;

    address[100] public buyers; // 매입자의 계정주소

    // 생성자 : 클래스 멤버변수 초기화
    // 배포할 때 사용된 계정이 contract의 소유자가 될 수 있다.
    constructor() public {

        // msg.sender : 현재 사용하는 계정으로 주소값을 가지고 있다.
        owner = msg.sender;
    }

    // 매물 구입 함수
    // payable : 함수가 Ether를 받아야 할 때 사용
    function buyRealEstate(uint _id, bytes32 _name, bytes32 _businessNumber) public payable {
        require(_id >= 1 && _id <= 99);

        buyers[_id] = msg.sender; // 매물을 구입하고 있는 현재 계정을 buyers 배열에 저장

        buyerInfo[_id] = Buyer(msg.sender, _name, _businessNumber);

        // msg.value : 송금받은 Ether를 말하며, 웨이만 허용하므로 front-end에서 미리 이더를 웨이로 변환시켜야 한다.
        owner.transfer(msg.value); // 매입가를 owner 주소로 송금한다.
    }

    // view : 데이터를 불러오는 읽기전용 함수들은 가스비가 들지 않는다.
    // 매입자의 정보를 불러오는 함수
    function getBuyerInfo(uint _id) public view returns (address, bytes32, bytes32) {
        Buyer memory buyer = buyerInfo[_id];
        return (buyer.buyAddress, buyer.name, buyer.businessNumber);
    }

    // 매입자의 계정주소를 불러오는 함수
    function getAllBuyers() public view returns (address[100] memory) {
        return buyers;
    }

}
